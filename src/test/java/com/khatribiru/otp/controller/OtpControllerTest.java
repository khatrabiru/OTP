package com.khatribiru.otp.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class OtpControllerTest {
    @Autowired
    OtpController otpController;

    @Test
    void createOTPSuccess(){
        ResponseEntity<Object> response = otpController.createOTP("10");
        Assertions.assertEquals(201, response.getStatusCodeValue());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void createOTPInvalidOTP(){
        ResponseEntity<Object> response = otpController.createOTP("123 bad TTL");
        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Invalid TTL.", response.getBody().toString());
    }
}
