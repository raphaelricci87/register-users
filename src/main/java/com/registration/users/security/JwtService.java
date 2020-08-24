package com.registration.users.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.registration.users.model.UserSystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signature-key}")
    private String signatureKey;

    public String generateToken (UserSystem user){
        long expString = Long.valueOf(expiration);
        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();
    }

    public Claims getClaims (String token) throws ExpiredJwtException{
        return Jwts
                    .parser()
                    .setSigningKey(signatureKey)
                    .parseClaimsJwt(token)
                    .getBody();
    }

    public boolean ValidToken(String token){

        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime localDateTime = expirationDate
                                            .toInstant()
                                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserLogin (String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }
}