package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"hsqldb"})
@AutoConfigureMockMvc
@AutoConfigureHttpGraphQlTester
public class AbstractClinicGraphqlTests {

    protected WebGraphQlTester managerRoleGraphQlTester;
    protected WebGraphQlTester userRoleGraphQlTester;
    protected WebGraphQlTester unauthorizedGraphqlTester;

    @BeforeEach
    void setupWebGraphqlTester(@Autowired WebGraphQlTester graphQlTester) {
        this.unauthorizedGraphqlTester = graphQlTester;

        this.userRoleGraphQlTester = graphQlTester.mutate()
            .headers(AbstractClinicGraphqlTests::withUserToken)
            .build();

        this.managerRoleGraphQlTester = graphQlTester.mutate()
            .headers(AbstractClinicGraphqlTests::withManagerToken)
            .build();
    }

    private static void withManagerToken(HttpHeaders headers) {
        headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNpIiwiaWF0IjoxNjA4ODg5NDQwLCJleHAiOjIzNjYyNzE4NDB9.XG0SEtHiidGuy2A1zy_BfixVMFOv3gGbfwGqEc3F-KU");
    }

    private static void withUserToken(HttpHeaders headers) {
        headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2UiLCJpYXQiOjE2MDg4ODk0NDAsImV4cCI6MjM2NjI3MTg0MH0.V36ynhDffqb9LQFsckOdk6lFhcVEDhOCFxFCQDAYG0o");
    }
}
