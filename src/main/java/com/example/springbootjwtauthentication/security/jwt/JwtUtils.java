package com.example.springbootjwtauthentication.security.jwt;


import com.example.springbootjwtauthentication.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.*;

@Component
@Slf4j
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${santi.app.jwtSecret}")
    private String jwtSecret;

    @Value("${santi.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        return generateTokenFromUsername(userPrincipal.getUsername(), userPrincipal.getId());
    }

    public String generateTokenFromUsername(String username, Long id) {

        return Jwts.builder()
                .setSubject(username)
                .signWith(HS512, jwtSecret)
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .setIssuedAt(new Date())
                .claim("uid", id)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {

//        log.warn("HOLA TE EXTAÑO: {} ", Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("uid").toString());

        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserIdFromJwtToken(HttpServletRequest http) {
        return getUserIdFromJwtToken(parseJwt(http));
    }

    public String getUserIdFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("uid").toString();
    }


    public String getUserNameFromJwtToken(HttpServletRequest http) {
        return getUserNameFromJwtToken(parseJwt(http));
    }

    public boolean validateJwtToken(HttpServletRequest request) {
        return validateJwtToken(parseJwt(request));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
