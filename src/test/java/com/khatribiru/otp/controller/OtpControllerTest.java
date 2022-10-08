package com.khatribiru.otp.controller;

import com.khatribiru.otp.service.OtpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.mockito.Mockito.when;

@SpringBootTest
public class OtpControllerTest {
    @Autowired
    OtpController otpController;

    @MockBean
    OtpService otpService;

    @Test
    void createOTPSuccess(){
        when(otpService.createOTP(10)).thenReturn("validOtp");
        ResponseEntity<Object> response = otpController.createOTP("10");
        Assertions.assertEquals(201, response.getStatusCodeValue());
        Assertions.assertEquals("validOtp", Objects.requireNonNull(response.getBody()).toString());
    }

    @Test
    void createOTPInvalidOTP(){
        ResponseEntity<Object> response = otpController.createOTP("123 bad TTL");
        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertEquals("Invalid TTL.", Objects.requireNonNull(response.getBody()).toString());
    }

    @Test
    void verifyOTPSuccess() {
        when(otpService.verifyOTP("validOtp")).thenReturn(true);
        ResponseEntity<Object> response = otpController.verifyOTP("validOtp");
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals("OTP verified successfully.", Objects.requireNonNull(response.getBody()).toString());
    }

    @Test
    void verifyOTPFailure() {
        when(otpService.verifyOTP("invalidOtp")).thenReturn(false);
        ResponseEntity<Object> response = otpController.verifyOTP("invalidOtp");
        Assertions.assertEquals(404, response.getStatusCodeValue());
        Assertions.assertEquals("OTP is not valid.", Objects.requireNonNull(response.getBody()).toString());
    }
}
