package com.project.zipsa.service;

import com.project.zipsa.dto.auth.ResponseLoginDto;
import com.project.zipsa.dto.auth.TokenDto;
import com.project.zipsa.dto.enums.GENERAL_FAIL_DETAIL;
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
import com.project.zipsa.util.SMSUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    @Value("${uuid.length}")
    private int uuidLen;

    private final UserRepository userRepository;
    private final CheckCodeRepository checkCodeRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private Users getUserNotDeleted(String userId) {
        return userRepository.findByUserIdAndUserStatus(userId, USER_STATUS.NORMAL)
                .orElseThrow(() -> new UnAuthenticatedException(GENERAL_FAIL_DETAIL.WRONG_USER_INFO.name()));
    }

    @Transactional
    public ResponseLoginDto login(String userId, String userPw) {
        Users user = getUserNotDeleted(userId);

        if(!passwordEncoder.matches(userPw, user.getUserPw())) {
            throw new UnAuthenticatedException(GENERAL_FAIL_DETAIL.WRONG_USER_INFO.name());
        }

        if (!(user.getUserRole() == USER_ROLE.USER || user.getUserRole() == USER_ROLE.ADMIN)) {
            throw new UnAuthorizedException(GENERAL_FAIL_DETAIL.WRONG_AUTHORITY.name());
        }

        TokenDto tokenDto = jwtProvider.createToken(user.getUserId(), List.of(user.getUserRole().getText()));

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshTokenKey(user.getUserId())
                .refreshTokenValue(tokenDto.getRefreshToken())
                .build();

        tokenRepository.deleteByRefreshTokenKey(userId);
        tokenRepository.saveAndFlush(refreshToken);
        return new ResponseLoginDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
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

    public void checkPhoneCode(String userPhone, String code) {
        boolean isValid = checkCodeRepository.findFirstByCheckCodeAndCheckCodeTypeAndDeviceOrderByCheckCodeIdxDesc(
                code, CHECK_CODE_TYPE.PHONE, userPhone).isPresent();
        if(!isValid) {
            throw new BadValidDeviceCodeException(GENERAL_FAIL_DETAIL.BAD_DEVICE_CODE.name());
        }
    }

    public String findId(FindIdRequestDto findIdRequestDto) {
        validateUserPhone(findIdRequestDto.getUserPhone());
        Users user = userRepository.findByUserNameAndUserStatusAndUserBirthAndUserPhone(
                                                        findIdRequestDto.getUserName(), USER_STATUS.NORMAL,
                                                        findIdRequestDto.getUserBirth(), findIdRequestDto.getUserPhone())
                .orElseThrow(() -> new UserNotFoundException(GENERAL_FAIL_DETAIL.NO_DATA.name()));
        return user.getUserId();
    }

    public void findPw(FindPwRequestDto findPwRequestDto) {
        validateUserPhone(findPwRequestDto.getUserPhone());
        userRepository.findByUserNameAndUserStatusAndUserBirthAndUserPhoneAndUserId(
                        findPwRequestDto.getUserName(), USER_STATUS.NORMAL, findPwRequestDto.getUserBirth(),
                        findPwRequestDto.getUserPhone(), findPwRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(GENERAL_FAIL_DETAIL.NO_DATA.name()));
    }

    @Transactional
    public void changePw(ChangePwRequestDto changePwRequestDto) {
        Users user = userRepository.findByUserIdAndUserStatus(changePwRequestDto.getUserId(), USER_STATUS.NORMAL)
                .orElseThrow(() -> new UserNotFoundException(GENERAL_FAIL_DETAIL.NO_DATA.name()));
        user.changePw(passwordEncoder.encode(changePwRequestDto.getUserPw()));
    }

    public GetMeResponseDto getMyInfo(String userId) {
        return new GetMeResponseDto(getUserNotDeleted(userId));
    }

    @Transactional
    public void changeAlert(ChangeAlertRequestDto changeAlertRequestDto, String userId) {
        Users user = getUserNotDeleted(userId);
        user.changeAlert(changeAlertRequestDto.getIsAlert(),
                        changeAlertRequestDto.getIsMarketingSMS(),
                        changeAlertRequestDto.getIsMarketingEmail());
    }

    @Transactional
    public void deleteMyInfo(DeleteMeRequestDto deleteMeRequestDto, String userId) {
        checkUserId(deleteMeRequestDto.getUserId(), userId);
        Users user = getUserNotDeleted(deleteMeRequestDto.getUserId());
        boolean isValidPw = passwordEncoder.matches(deleteMeRequestDto.getUserPw(), user.getUserPw());
        if(isValidPw) {
            user.deleteMyInfo();
        }else {
            throw new DeleteMyInfoException("회원탈퇴에 실패했습니다. 아이디 혹은 패스워드를 확인하세요.");
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
    public void changeUserProfileImage(MultipartFile profileImage, String userId) {
        Users user = getUserNotDeleted(userId);
        String userProfileImage = user.getUserProfileImage();
    }

    @Transactional
    public String changeUserId(String newUserId, String userId) {
        getUserNotDeleted(userId).changeUserId(newUserId);
        return newUserId;
    }

    private void checkUserId(String inputUserId, String tokenUserId) {
        if(!inputUserId.equals(tokenUserId)) {
            throw new UserIdNotSameWithTokenKeyException("아이디가 잘못되었습니다.");
        }
    }

    private void checkUserPhoneDuplicate(String phone) {
        boolean isDuplicate = userRepository.findByUserPhone(phone).isPresent();
        if(isDuplicate) {
            throw new DuplicateUserPhoneException(GENERAL_FAIL_DETAIL.DUPLICATE_PHONE.name());
        }
    }

    private void validateUserPhone(String phone) {
        // 길이가 12 ~ 13자리인지 확인
        if(!(phone.length() == 12 || phone.length() == 13)) {
            throw new BadUserPhoneException(GENERAL_FAIL_DETAIL.BAD_TYPE_PHONE.name());
        }

        // '-'를 제외한 길이가 10 ~ 11자리인지 확인
        String rawPhone = phone.replace("-", "");
        if(!(rawPhone.length() == 10 || rawPhone.length() == 11)) {
            throw new BadUserPhoneException(GENERAL_FAIL_DETAIL.BAD_TYPE_PHONE.name());
        }

        // 숫자만 포함하는지 검사
        if(!StringUtils.isNumeric(rawPhone)) {
            throw new BadUserPhoneException(GENERAL_FAIL_DETAIL.BAD_TYPE_PHONE.name());
        }


        int firPos = phone.indexOf("-");
        int secPos = phone.lastIndexOf("-");

        String firStr = phone.substring(0, firPos);
        String secStr = phone.substring(firPos + 1, secPos);
        String thirdStr = phone.substring(secPos + 1);

        // 첫 번째 자리가 '011' 혹은 '010'인지 확인
        if(!(firStr.equals("011") || firStr.equals("010"))) {
            throw new BadUserPhoneException(GENERAL_FAIL_DETAIL.BAD_TYPE_PHONE.name());
        }

        // 두번째 자리가 3 ~ 4자리인지 확인
        if(!(secStr.length() == 3 || secStr.length() == 4)) {
            throw new BadUserPhoneException(GENERAL_FAIL_DETAIL.BAD_TYPE_PHONE.name());
        }

        // 마지막 자리가 4자리인지 확인
        if(!(thirdStr.length() == 4)) {
            throw new BadUserPhoneException(GENERAL_FAIL_DETAIL.BAD_TYPE_PHONE.name());
        }

    }

}