package com.assessment.bookstore.security.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    // JWT Token is in the form "Bearer token". Remove Bearer word and get only the
    // Token starts from index number 7
    public static final int JWT_TOKEN_AUTHORIZATION_START_INDEX = 7;

    @Value("${jwt.signingSecret}")
    private String secret;

    @Value("${jwt.expirationInSeconds}")
    private Long expirationInSeconds;

    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Long getUserIdFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("id", Long.class);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {

        try {

            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        } catch (SignatureException e) {

            LOGGER.error("Invalid JWT signature: {}", e.getMessage());

            throw new SignatureException(e.getMessage());

        } catch (MalformedJwtException e) {

            LOGGER.error("Invalid JWT token: {}", e.getMessage());

            throw new MalformedJwtException(e.getMessage());

        } catch (ExpiredJwtException e) {

            LOGGER.error("JWT token is expired: {}", e.getMessage());

            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());

        } catch (UnsupportedJwtException e) {

            LOGGER.error("JWT token is unsupported: {}", e.getMessage());

            throw new UnsupportedJwtException(e.getMessage());

        } catch (IllegalArgumentException e) {

            LOGGER.error("JWT claims string is empty: {}", e.getMessage());

            throw new IllegalArgumentException();

        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());

        return doGenerateToken(claims, (userDetails).getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        Date creationDate = new Date(System.currentTimeMillis());
        Date expirationDate = calculateExpirationDate(creationDate);

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(creationDate)
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Date calculateExpirationDate(Date creationDate) {
        return new Date(creationDate.getTime() + expirationInSeconds * 1000);
    }

    public String getUsernameFromAuthorizationHeader(String authorizationHeader) {

        String username = null;
        String jwtToken;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            jwtToken = authorizationHeader.substring(JWT_TOKEN_AUTHORIZATION_START_INDEX);

            try {

                username = getUsernameFromToken(jwtToken);

                LOGGER.debug("Received a valid token from username:{}", username);

            } catch (IllegalArgumentException e) {

                LOGGER.warn("Unable to get JWT Token", e);

            } catch (ExpiredJwtException e) {

                LOGGER.warn("JWT Token has expired", e);

            }
        } else {
            LOGGER.warn("JWT Token does not begin with Bearer String");
        }

        return username;
    }

}
