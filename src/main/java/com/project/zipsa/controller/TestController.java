package com.project.zipsa.controller;

import com.project.zipsa.entity.Test;
import com.project.zipsa.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping(value = "/test/test")
    public String a(Principal principal) {
        log.error("data = {}", principal.getName());
        testService.createTest(new Test("1", LocalDateTime.now().toString()));
        return "ok";
    }

    @GetMapping(value = "/tests/tests")
    public String b() {
        String url = "jdbc:mariadb://mariadb-ver10-01.c3edzk8fothj.ap-northeast-2.rds.amazonaws.com:3306/ZIPSA_DEV?characterEncoding=UTF-8&serverTimezone=Asia/Seoul&useUnicode=true";
        String username = "admin";
        String password = "Na6080su12!!";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setPoolSize(2);
        encryptor.setPassword("u)hG>cTPjEe.Qk@");
        encryptor.setAlgorithm("PBEWithSHA256And128BitAES-CBC-BC");

        String eurl = encryptor.encrypt(url);
        String ename = encryptor.encrypt(username);
        String epassword = encryptor.encrypt(password);
        System.out.println(eurl);
        System.out.println(ename);
        System.out.println(epassword);

        System.out.println(encryptor.decrypt(eurl));
        System.out.println(encryptor.decrypt(ename));
        System.out.println(encryptor.decrypt(epassword));

        System.out.println(System.getenv("encrypt.key"));
        System.out.println(System.getenv("encrypt.algorithm"));

        return "a";
    }

}
