package ru.ageev.temperatureSensor.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Base64;

@Repository
public class JwtTokenRepository implements CsrfTokenRepository {
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String secretKey = "yourBase64EncodedSecretKey";

        String token = Jwts.builder()
                .setSubject("sensor")
                .claim("sensor", "sensor")
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();


        System.out.println(Arrays.toString(request.getCookies()));

        return new DefaultCsrfToken("x-csrf-token", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader(token.getHeaderName(), token.getToken());
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String token = request.getHeader("x-csrf-token");

        if (token != null) {
            return new DefaultCsrfToken("x-csrf-token", "_csrf", token);
        }

        return null;
    }
}
