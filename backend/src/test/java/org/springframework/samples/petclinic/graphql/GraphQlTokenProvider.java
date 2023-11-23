package org.springframework.samples.petclinic.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.samples.petclinic.security.JwtTokenService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GraphQlTokenProvider {

    @Autowired
    private JwtTokenService tokenService;

    protected String createManagerToken() {
        return tokenService.generateToken("susi", List.of( () -> "MANAGER"), Instant.now().plus(1, ChronoUnit.HOURS));
    }

    protected String createUserToken() {
        return tokenService.generateToken("joe", List.of( () -> "USER"), Instant.now().plus(1, ChronoUnit.HOURS));
    }


}
