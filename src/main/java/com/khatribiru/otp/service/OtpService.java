package com.khatribiru.otp.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Date;

@Service
public class OtpService {

    static KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    public String createOTP(long ttl) {
        long ttlInMillis = ttl * 1000;
        JwtBuilder builder = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + ttlInMillis))
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256);
        return builder.compact();
    }

    public boolean verifyOTP(String otp) {
        try {
            Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(otp);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
