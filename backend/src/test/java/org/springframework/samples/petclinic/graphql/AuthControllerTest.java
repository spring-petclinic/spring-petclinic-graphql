package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureHttpGraphQlTester
public class AuthControllerTest {
    @Test
    void shouldReturnCurrentUser(@Autowired WebGraphQlTester graphQlTester) {
        graphQlTester.mutate()
            .headers(TestTokens::withUserToken)
            .build()
            .documentName("meQuery")
            .execute()
            .path("me.username").entity(String.class).isEqualTo("joe")
            .path("me.fullname").entity(String.class).isEqualTo("Joe Hill");
    }

    @Test
    void shouldReturnUnauthorizedWithoutToken(@Autowired WebGraphQlTester graphQlTester) {

        assertThatThrownBy(() ->
            graphQlTester.mutate()
                .build()
                .documentName("meQuery")
                .executeAndVerify())
            .hasMessage("Status expected:<200 OK> but was:<401 UNAUTHORIZED>");
    }

}
