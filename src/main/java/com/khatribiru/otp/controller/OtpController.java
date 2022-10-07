package com.khatribiru.otp.controller;

import com.khatribiru.otp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    OtpService otpService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Object> createOTP(@RequestParam(required = false, defaultValue = "30") String ttl){
        if(!validateTTL(ttl)) {
            return ResponseEntity
                    .status(400)
                    .body("Invalid TTL.");
        }
        return ResponseEntity
                .status(201)
                .body(otpService.createOTP(Long.parseLong(ttl)));
    }

    @GetMapping("/verify/{otp}")
    @ResponseBody
    public ResponseEntity<Object> verifyOTP(@PathVariable String otp){
        if(!otpService.verifyOTP(otp)) {
            return ResponseEntity
                    .status(404)
                    .body("OTP is not valid.");
        }
        return ResponseEntity
                .status(200)
                .body("OTP verified successfully.");
    }

    private boolean validateTTL(String ttl) {
        try {
            long val = Long.parseLong(ttl);
            return val >= 0;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
