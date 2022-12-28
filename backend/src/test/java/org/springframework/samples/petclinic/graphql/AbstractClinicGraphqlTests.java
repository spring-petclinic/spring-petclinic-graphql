package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;
import org.springframework.http.HttpHeaders;
import org.springframework.samples.petclinic.security.JwtTokenService;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
@ActiveProfiles(profiles = {"hsqldb"})
@AutoConfigureMockMvc
@AutoConfigureHttpGraphQlTester
public class AbstractClinicGraphqlTests {

    @Autowired
    private JwtTokenService tokenService;
    protected WebGraphQlTester managerRoleGraphQlTester;
    protected WebGraphQlTester userRoleGraphQlTester;
    protected WebGraphQlTester unauthorizedGraphqlTester;

    @BeforeEach
    void setupWebGraphqlTester(@Autowired WebGraphQlTester graphQlTester) {
        this.unauthorizedGraphqlTester = graphQlTester;

        this.userRoleGraphQlTester = graphQlTester.mutate()
            .headers(this::withUserToken)
            .build();

        this.managerRoleGraphQlTester = graphQlTester.mutate()
            .headers(this::withManagerToken)
            .build();
    }

    private void withManagerToken(HttpHeaders headers) {
        var token = tokenService.generateToken("susi", List.of( () -> "MANAGER"), Instant.now().plus(1, ChronoUnit.HOURS));
        headers.setBearerAuth(token);
    }

    private void withUserToken(HttpHeaders headers) {
        var token = tokenService.generateToken("joe", List.of( () -> "USER"), Instant.now().plus(1, ChronoUnit.HOURS));
        headers.setBearerAuth(token);
    }
}
