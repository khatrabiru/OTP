package com.khatribiru.otp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class SystemTest {
    private static final String DOMAIN = "http://localhost:8080/api/otp";
    private static final String CREATE_OTP = DOMAIN + "/create";
    private static final String VERIFY_OTP = DOMAIN + "/verify/";

    @Test
    void createOTPSuccess() {
        RestTemplate restTemplate = new RestTemplate();
        String url = CREATE_OTP + "?ttl=20";
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpHeaders(), String.class);
        Assertions.assertEquals(201, response.getStatusCodeValue());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void createOTPFailure() {
        RestTemplate restTemplate = new RestTemplate();
        String url = CREATE_OTP + "?ttl=abcd";
        try {
            restTemplate.postForEntity(url, new HttpHeaders(), String.class);
        } catch (HttpClientErrorException ex) {
            Assertions.assertEquals(400, ex.getRawStatusCode());
            Assertions.assertEquals("Invalid TTL.", ex.getResponseBodyAsString());
        }
    }

    @Test
    void verifyOTPSuccess() {
        RestTemplate restTemplate = new RestTemplate();
        // create valid otp
        String url = CREATE_OTP + "?ttl=20";
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpHeaders(), String.class);
        Assertions.assertEquals(201, response.getStatusCodeValue());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());

        String otp  = response.getBody();
        url = VERIFY_OTP + otp;
        response = restTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("OTP verified successfully.", response.getBody());
    }

    @Test
    void verifyOTPInvalidOtp() {
        RestTemplate restTemplate = new RestTemplate();
        String otp  = "invalid";
        String url = VERIFY_OTP + otp;
        try {
            restTemplate.getForEntity(url, String.class);
            Assertions.fail();
        } catch (HttpClientErrorException ex) {
            Assertions.assertEquals(404, ex.getRawStatusCode());
            Assertions.assertEquals("OTP is not valid.", ex.getResponseBodyAsString());
        }
    }

    @Test
    void verifyOTPExpired() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        // create valid otp
        String url = CREATE_OTP + "?ttl=1";
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpHeaders(), String.class);
        Assertions.assertEquals(201, response.getStatusCodeValue());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());

        Thread.sleep(1000);
        String otp  = response.getBody();
        url = VERIFY_OTP + otp;
        try {
            restTemplate.getForEntity(url, String.class);
            Assertions.fail();
        } catch (HttpClientErrorException ex) {
            Assertions.assertEquals(404, ex.getRawStatusCode());
            Assertions.assertEquals("OTP is not valid.", ex.getResponseBodyAsString());
        }
    }
}
