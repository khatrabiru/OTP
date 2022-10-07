package com.khatribiru.otp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OtpServiceTest {
    @Autowired
    OtpService otpService;

    @Test
    void createOTPSuccess(){
        String otp = otpService.createOTP(10);
        Assertions.assertNotNull(otp);
        Assertions.assertTrue(otp.length() > 10);
    }

    @Test
    void verifyOTPFalse(){
        boolean verify = otpService.verifyOTP("dummy.otp");
        Assertions.assertFalse(verify);
    }

    @Test
    void verifyOTPTrue(){
        String otp = otpService.createOTP(10);
        boolean verify = otpService.verifyOTP(otp);
        Assertions.assertTrue(verify);
    }

    @Test
    void verifyOTPExpired() throws InterruptedException {
        String otp = otpService.createOTP(1);
        Thread.sleep(1000);
        boolean verify = otpService.verifyOTP(otp);
        Assertions.assertTrue(verify);
    }
}
