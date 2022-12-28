package org.springframework.samples.petclinic.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Based con code taken from Dan Vega https://github.com/danvega/jwt-username-password/blob/master/src/main/java/dev/danvega/jwt/service/TokenService.java
 */
@Service
public class JwtTokenService {

    private final JwtEncoder encoder;

    public JwtTokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication) {
        return generateToken(authentication.getName(),
            authentication.getAuthorities(),
            Instant.now().plus(1, ChronoUnit.HOURS)
        );
    }

    public String generateToken(String name, Collection<? extends GrantedAuthority> authorities, Instant expiresAt) {
        String scope = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(Instant.now())
            .expiresAt(expiresAt)
            .subject(name)
            .claim("scope", scope)
            .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
