package ru.ageev.temperatureSensor.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    @Value("${spring.security.secretKey}")
    private String secretKey;

    public String generateToken(UserDetails sensorDetails) {
        Map<String, Object> claims = new Hashtable<>();

        if (sensorDetails.getAuthorities() != null) {
            List<String> roles = sensorDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            claims.put("roles", roles);
        } else {
            claims.put("roles", Collections.emptyList());
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(sensorDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaims(token).get("roles", List.class);
    }
}
