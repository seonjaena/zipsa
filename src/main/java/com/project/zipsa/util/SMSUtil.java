package com.project.zipsa.util;

import com.project.zipsa.dto.enums.GENERAL_FAIL_DETAIL;
import com.project.zipsa.exception.custom.SendSMSFailException;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Random;

@Slf4j
public class SMSUtil {

    // API KEY
    private static final String API_KEY = "NCSXCM7B4PF7EHQB";
    // API 비밀KEY
    private static final String API_SECRET_KEY = "KZGN8DOOKPEICT2QSLMRKJE0KW82VMYT";
    // 보내는 사람 전화번호
    private static final String FROM_PHONE = "01025392535";

    public String sendSMS(String to) {

        Message message = new Message(API_KEY, API_SECRET_KEY);
        String code = makeRandom();
        HashMap<String, String> map = new HashMap<>();
        map.put("to", to);
        map.put("from", FROM_PHONE);
        map.put("text", String.format("안녕하세요. 인증번호는 [ %s ] 입니다.", code));
        map.put("type", "sms");

        try {
            JSONObject result = message.send(map);
        }catch(CoolsmsException e) {
            throw new SendSMSFailException(GENERAL_FAIL_DETAIL.SEND_SMS.name());
        }
        return code;
    }

    private String makeRandom() {
        // 인증 값
        StringBuilder code = new StringBuilder();

        // 랜덤 값 생성을 위한 랜덤 함수
        Random random = new Random();

        // 인증 코드에 난수들을 넣는다.(6자리)
        for(int i = 0; i < 6; i++) {
            String currentStr = String.valueOf(System.currentTimeMillis());

            int endPos = random.nextInt(currentStr.length() - 1) + 1;
            int startPos = random.nextInt(endPos);

            long ranNum = Long.parseLong(currentStr.substring(startPos, endPos)) % 10;
            code.append(ranNum);
        }

        return code.toString();

    }

}
