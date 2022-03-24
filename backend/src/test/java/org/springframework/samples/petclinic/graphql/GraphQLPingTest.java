package org.springframework.samples.petclinic.graphql;

import org.hsqldb.Tokens;
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

        this.graphQlTester.mutate()
            .headers(TestTokens::withManagerToken)
            .build()
            .document(query)
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
            this.graphQlTester.document(query)
                .executeAndVerify())
            .hasMessage("Status expected:<200 OK> but was:<401 UNAUTHORIZED>");
    }

}
