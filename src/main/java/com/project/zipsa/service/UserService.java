package com.project.zipsa.service;

import com.project.zipsa.dto.auth.ResponseLoginDto;
import com.project.zipsa.dto.auth.TokenDto;
import com.project.zipsa.dto.enums.PHONE_CHECK_TYPE;
import com.project.zipsa.dto.user.*;
import com.project.zipsa.entity.CheckCode;
import com.project.zipsa.entity.RefreshToken;
import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.enums.CHECK_CODE_TYPE;
import com.project.zipsa.entity.enums.USER_ROLE;
import com.project.zipsa.entity.enums.USER_STATUS;
import com.project.zipsa.exception.custom.*;
import com.project.zipsa.repository.CheckCodeRepository;
import com.project.zipsa.repository.TokenRepository;
import com.project.zipsa.repository.UserRepository;
import com.project.zipsa.security.JwtProvider;
import com.project.zipsa.util.FileUtil;
import com.project.zipsa.util.SMSUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final CheckCodeRepository checkCodeRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final FileUtil fileUtil;
    private final MessageSource messageSource;

    private Users getUserNotDeleted(String userId) {
        return userRepository.findByUserIdAndUserStatus(userId, USER_STATUS.NORMAL)
                .orElseThrow(() -> new UnAuthenticatedException(messageSource.getMessage("error.user.info", null, Locale.KOREA)));
    }

    @Transactional
    public ResponseLoginDto login(String userId, String userPw) {
        Users user = getUserNotDeleted(userId);

        if(!passwordEncoder.matches(userPw, user.getUserPw())) {
            throw new UnAuthenticatedException(messageSource.getMessage("error.user.login", null, Locale.KOREA));
        }

        if (!(user.getUserRole() == USER_ROLE.USER || user.getUserRole() == USER_ROLE.ADMIN)) {
            throw new UnAuthorizedException(messageSource.getMessage("error.user.authority", null, Locale.KOREA));
        }

        TokenDto tokenDto = jwtProvider.createToken(user.getUserId(), List.of(user.getUserRole().getText()));

        tokenRepository.save(new RefreshToken(tokenDto.getRefreshToken(), user.getUserId()));

        return new ResponseLoginDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    public String refreshAccessToken(String refreshToken) {
        Users user = getUserNotDeleted(tokenRepository.find(refreshToken).getUserId());
        try {
            return jwtProvider.refreshAccessToken(user.getUserId(), List.of(user.getUserRole().getText()));
        }catch(Exception e) {
            throw new RefreshTokenExpireException(messageSource.getMessage("error.jwt.refresh.expire", null, Locale.KOREA));
        }
    }

    public List<JoinPageResponseDto> joinPage() {
        return userRepository.findAll().stream()
                .map(u -> new JoinPageResponseDto(u.getUserId(), u.getUserNickname()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void join(JoinRequestDto joinRequestDto) {
        joinRequestDto.setUserPw(passwordEncoder.encode(joinRequestDto.getUserPw()));
        userRepository.save(Users.from(joinRequestDto));
//        rabbitMqUtil.createQueue(joinRequestDto.getUserId());
    }

    @Transactional
    public void checkPhone(String userPhone, PHONE_CHECK_TYPE type) {
        if(type == PHONE_CHECK_TYPE.JOIN) {
            checkUserPhoneDuplicate(userPhone);
        }
        validateUserPhone(userPhone);
        SMSUtil smsUtil = new SMSUtil();
        String code = smsUtil.sendSMS(userPhone.replace("-", ""));
        checkCodeRepository.save(new CheckCode(code, CHECK_CODE_TYPE.PHONE, userPhone));
    }

    public boolean checkExistUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public boolean checkExistNickname(String userNickname) {
        return userRepository.existsByUserNickname(userNickname);
    }

    public void checkPhoneCode(String userPhone, String code) {
        boolean isValid = checkCodeRepository.findFirstByCheckCodeAndCheckCodeTypeAndDeviceOrderByCheckCodeIdxDesc(
                code, CHECK_CODE_TYPE.PHONE, userPhone).isPresent();
        if(!isValid) {
            throw new BadValidDeviceCodeException(messageSource.getMessage("error.user.phone.check.code", null, Locale.KOREA));
        }
    }

    public String findId(FindIdRequestDto findIdRequestDto) {
        validateUserPhone(findIdRequestDto.getUserPhone());
        Users user = userRepository.findByUserNameAndUserStatusAndUserBirthAndUserPhone(
                                                        findIdRequestDto.getUserName(), USER_STATUS.NORMAL,
                                                        findIdRequestDto.getUserBirth(), findIdRequestDto.getUserPhone())
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.info", null, Locale.KOREA)));
        return user.getUserId();
    }

    public void findPw(FindPwRequestDto findPwRequestDto) {
        validateUserPhone(findPwRequestDto.getUserPhone());
        userRepository.findByUserNameAndUserStatusAndUserBirthAndUserPhoneAndUserId(
                        findPwRequestDto.getUserName(), USER_STATUS.NORMAL, findPwRequestDto.getUserBirth(),
                        findPwRequestDto.getUserPhone(), findPwRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.info", null, Locale.KOREA)));
    }

    @Transactional
    public void changePwUnlogined(ChangePwUnloginedRequestDto changePwRequestDto) {
        Users user = userRepository.findByUserIdAndUserStatus(changePwRequestDto.getUserId(), USER_STATUS.NORMAL)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.info", null, Locale.KOREA)));
        user.changePw(passwordEncoder.encode(changePwRequestDto.getNewPw1()));
    }

    @Transactional
    public void changePwLogined(String userId, ChangePwLoginedRequestDto changePwRequestDto) {
        Users user = userRepository.findByUserIdAndUserStatus(userId, USER_STATUS.NORMAL)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("error.user.info", null, Locale.KOREA)));
        user.changePw(passwordEncoder.encode(changePwRequestDto.getNewPw1()));
    }

    public GetMeResponseDto getMyInfo(String userId) {
        Users user = getUserNotDeleted(userId);
        GetMeResponseDto userDto = new GetMeResponseDto(user);
        userDto.getUserProfileImage(fileUtil.getFileURL(user.getUserProfileImage()));
        return userDto;
    }

    @Transactional
    public void changeAlert(boolean isAlert, String userId) {
        Users user = getUserNotDeleted(userId);
        user.changeAlert(isAlert);
    }

    @Transactional
    public void deleteMyInfo(DeleteMeRequestDto deleteMeRequestDto, String userId) {
        checkUserId(deleteMeRequestDto.getUserId(), userId);
        Users user = getUserNotDeleted(deleteMeRequestDto.getUserId());
        boolean isValidPw = passwordEncoder.matches(deleteMeRequestDto.getUserPw(), user.getUserPw());
        if(isValidPw) {
            user.deleteMyInfo();
        }else {
            throw new DeleteMyInfoException(messageSource.getMessage("error.user.delete", null, Locale.KOREA));
        }
    }

    @Transactional
    public String changeUserNickname(String userNickname, String userId) {
        getUserNotDeleted(userId).changeNickname(userNickname);
        return userNickname;
    }

    @Transactional
    public String changeUserPhone(String userPhone, String userId) {
        getUserNotDeleted(userId).changeUserPhone(userPhone);
        return userPhone;
    }

    @Transactional
    public LocalDate changeUserBirth(LocalDate userBirth, String userId) {
        getUserNotDeleted(userId).changeUserBirth(userBirth);
        return userBirth;
    }

    @Transactional
    public String changeUserProfileImage(MultipartFile profileImage, String userId) throws IOException {
        Users user = getUserNotDeleted(userId);
        log.info("user name = {}, user nickname = {}", user.getUserName(), user.getUserNickname());
        String userProfileImage = user.getUserProfileImage();
        log.info("origin user profile image = {}", userProfileImage);
        log.info("new user profile image = {}", profileImage.getOriginalFilename());
        String savedFileName = fileUtil.upload(profileImage);
        user.changeUserProfileImage(savedFileName);
        log.info("user profile image changed. profile = {}", savedFileName);
        return fileUtil.getFileURL(savedFileName);
    }

    @Transactional
    public String changeUserId(String newUserId, String userId) {
        getUserNotDeleted(userId).changeUserId(newUserId);
        return newUserId;
    }

    private void checkUserId(String inputUserId, String tokenUserId) {
        if(!inputUserId.equals(tokenUserId)) {
            throw new UserIdNotSameWithTokenKeyException(messageSource.getMessage("error.user.info", null, Locale.KOREA));
        }
    }

    private void checkUserPhoneDuplicate(String phone) {
        boolean isDuplicate = userRepository.findByUserPhone(phone).isPresent();
        if(isDuplicate) {
            throw new DuplicateUserPhoneException(messageSource.getMessage("error.user.phone.duplicate", null, Locale.KOREA));
        }
    }

    private void validateUserPhone(String phone) {

        String message = messageSource.getMessage("error.user.phone.bad.form", null, Locale.KOREA);

        // 길이가 12 ~ 13자리인지 확인
        if(!(phone.length() == 12 || phone.length() == 13)) {
            throw new BadUserPhoneException(message);
        }

        // '-'를 제외한 길이가 10 ~ 11자리인지 확인
        String rawPhone = phone.replace("-", "");
        if(!(rawPhone.length() == 10 || rawPhone.length() == 11)) {
            throw new BadUserPhoneException(message);
        }

        // 숫자만 포함하는지 검사
        if(!StringUtils.isNumeric(rawPhone)) {
            throw new BadUserPhoneException(message);
        }


        int firPos = phone.indexOf("-");
        int secPos = phone.lastIndexOf("-");

        String firStr = phone.substring(0, firPos);
        String secStr = phone.substring(firPos + 1, secPos);
        String thirdStr = phone.substring(secPos + 1);

        // 첫 번째 자리가 '011' 혹은 '010'인지 확인
        if(!(firStr.equals("011") || firStr.equals("010"))) {
            throw new BadUserPhoneException(message);
        }

        // 두번째 자리가 3 ~ 4자리인지 확인
        if(!(secStr.length() == 3 || secStr.length() == 4)) {
            throw new BadUserPhoneException(message);
        }

        // 마지막 자리가 4자리인지 확인
        if(!(thirdStr.length() == 4)) {
            throw new BadUserPhoneException(message);
        }

    }

}
