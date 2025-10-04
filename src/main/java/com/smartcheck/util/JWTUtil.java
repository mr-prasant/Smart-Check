package com.smartcheck.util;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    // @Value annotation is now used with a non-static field, which is the standard and correct way.
    // Spring will inject the value of "jwt-secret" from your application properties file into this field.
    @Value("${jwt-secret}")
    private String secret;


    // A private helper method to get the SecretKey from the secret string.
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generates a new JWT for a given user email.
     * This is an instance method, so you need a JWTUtil object to call it.
     *
     * @param email The user's email, which will be the subject of the token.
     * @return The generated JWT as a compact string.
     */
    public String generateToken(String email, List<String> roles) {
        long ms = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(new Date(ms))
                .setExpiration(new Date(ms + (30 * 60 * 1000))) // for 30 minutes
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * Extracts the claims (payload) from a given JWT.
     * The method also implicitly validates the token's signature using the secret key.
     *
     * @param token The JWT string to be parsed.
     * @return A Claims object containing the token's payload.
     */
    public Claims extractClaims(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(getSecretKey()).build();
        Jws<Claims> claims = parser.parseClaimsJws(token);
        return claims.getBody();
    }

    /**
     * Extracts the subject (user's email) from a JWT.
     *
     * @param token The JWT string.
     * @return The email string from the token's subject claim.
     */
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Extracts the subject (user's roles) from a JWT.
     *
     * @param token The JWT string.
     * @return The roles string from the token's subject claim.
     */
    public List<String> extractRoles(String token) {
        return (List<String>) extractClaims(token).get("roles");
    }

    /**
     * Checks if a JWT has expired.
     *
     * @param token The JWT string.
     * @return true if the token's expiration date is before the current date, otherwise false.
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    /**
     * Validates a JWT by checking if the email matches and if the token is not expired.
     *
     * @param email The expected email.
     * @param token The JWT string to validate.
     * @return true if the token is valid, otherwise false.
     */
    public boolean validateToken(String email, String token) {
        // A valid token should NOT be expired. The `!` operator negates the result of `isTokenExpired()`.
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }
}