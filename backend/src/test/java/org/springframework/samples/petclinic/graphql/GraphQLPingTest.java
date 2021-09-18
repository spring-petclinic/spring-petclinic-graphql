package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class GraphQLPingTest {
    @Autowired
    private WebGraphQlTester graphQlTester;

    /**
     * EXAMPLE:
     * --------------------------
     *
     * HTTP endpoint authentification using JWT works
     */
    @Test
    public void meReturnsCurrentUser() {
        String query = "query { me { username } } ";

        this.graphQlTester.query(query)
            .headers(TestTokens::withManagerToken)
            .execute()
            .path("me.username").entity(String.class).isEqualTo("susi");
    }

    /**
     * EXAMPLE:
     * --------------------------
     *
     * HTTP endpoint authentification using JWT works ("/graphql" HTTP endoint is not
     * accessible at all when invoked without token)
     */
    @Test
    public void unauthorized() {
        String query = "query { ping } ";

        assertThatThrownBy(() ->
            this.graphQlTester.query(query)
                .executeAndVerify())
            .hasMessage("Status expected:<200 OK> but was:<401 UNAUTHORIZED>");
    }

}
