package com.softlaboratory.springbootminiproject.security;

import com.softlaboratory.springbootminiproject.domain.dao.UserDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private Long expiration = 1000L * 60 * 60; // exp = ms * s * h  --- 1 hour

    public String generateToken(Authentication authentication) {
        final UserDao userDao = (UserDao)authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expire = new Date(now.getTime() + expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDao.getUsername());

        return Jwts.builder()
                .setId(userDao.getId().toString())
                .setSubject("Subject")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key)
                .compact();

    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            System.out.println(e);
        }
        return false;

    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username").toString();
    }

}
