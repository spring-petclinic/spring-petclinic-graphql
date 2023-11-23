package org.springframework.samples.petclinic.graphql;

import com.github.dockerjava.api.model.ContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.WebGraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.http.HttpHeaders;
import org.springframework.samples.petclinic.PetClinicTestDbConfiguration;
import org.springframework.samples.petclinic.security.JwtTokenService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureHttpGraphQlTester
@Import(PetClinicTestDbConfiguration.class)
@Transactional
public class AbstractClinicGraphqlTests extends GraphQlTokenProvider {

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
        headers.setBearerAuth(createManagerToken());
    }

    private void withUserToken(HttpHeaders headers) {
        headers.setBearerAuth(createUserToken());
    }
}
