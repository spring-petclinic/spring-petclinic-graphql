package org.springframework.samples.petclinic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.petclinic.auth.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Note that this is an example only. DO NOT IMPLEMENT OWN SECURITY CODE IN REAL PRODUCTION APPS !!!!!!!!!!
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Service
public class JwtTokenService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jwt.expirationInMs:7200000}")
    private int jwtExpirationInMs;

    private final SecretKey secretKey;

    public JwtTokenService(@Value("${jwt.secretString:fasdfahsdufak4923674asbclbca73,f,a,dfw}") String secretString) {
        this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    /** Creates a token that will never expire and will be stable accross re-starts
     * as longs as jwt.secretString does not change.
     *
     * This token can be used for easier testing using command line tools etc.
     * YOU SHOULD NEVER DO THIS IN 'REAL' PRODUCTION APPS
     */
    @PostConstruct
    void createNeverExpiringToken() throws Exception {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String neverExpiringManagerToken = Jwts.builder()
            .setSubject("susi")
            .setIssuedAt(f.parse("25.12.2020 10:44"))
            .setExpiration(f.parse("25.12.2044 10:44"))
            .signWith(secretKey)
            .compact();

        String neverExpiringUserToken = Jwts.builder()
            .setSubject("joe")
            .setIssuedAt(f.parse("25.12.2020 10:44"))
            .setExpiration(f.parse("25.12.2044 10:44"))
            .signWith(secretKey)
            .compact();
        logger.info("\n\nNever Expiring JWT Token\n\n - ROLE_MANAGER: '{}'\n    As HTTP Header: 'Authorization: Bearer {}'\n\n - ROLE_USER: '{}'\n    As HTTP Header: 'Authorization: Bearer {}'\n", neverExpiringManagerToken, neverExpiringManagerToken, neverExpiringUserToken, neverExpiringUserToken);
    }

    public String createTokenForUser(User user) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

    public boolean isValidToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            logger.info("Invalid JWT token: " + ex);
        }

        return false;
    }
}
