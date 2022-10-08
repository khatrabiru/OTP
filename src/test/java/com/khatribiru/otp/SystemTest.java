package com.khatribiru.otp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SystemTest {
    private static final String DOMAIN = "localhost:8080/api/otp";
    private static final String CREATE_OTP = DOMAIN + "/create";
    private static final String VERIFY_OTP = DOMAIN + "/verify/%s";

    @Test
    void createOTPSuccess() {

    }

    @Test
    void createOTPFailure() {

    }

    @Test
    void verifyOTPSuccess() {

    }

    @Test
    void verifyOTPFailure() {

    }
}
