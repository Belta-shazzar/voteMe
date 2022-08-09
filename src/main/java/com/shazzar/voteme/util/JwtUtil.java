package com.shazzar.voteme.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    
    private final AuthenticationManager authenticationManager;
    private static final int TOKEN_VALIDITY = 3600 * 5;
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Lazy
    public JwtUtil(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String getUserNameFromToken(String jwt) {
        return getClaimsFromToken(jwt, Claims::getSubject);
    }

    public <T> T getClaimsFromToken(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String jwt) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
    }

    public Boolean validateToken(String jwt, UserDetails userDetails) {
        final String username = getUserNameFromToken(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private Boolean isTokenExpired(String jwt) {
        final Date date = getExpirationFromToken(jwt);
        return date.before(new Date());
    }

    public Date getExpirationFromToken(String jwt) {
        return getClaimsFromToken(jwt, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("voteMe")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @SneakyThrows
    public void authenticate(String username, String password) {
        
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException ex) {
            throw new Exception("USER_DISABLED", ex);
        } catch (BadCredentialsException ex) {
            throw new Exception("INVALID_CREDENTIALS", ex);
        }
    }

}
