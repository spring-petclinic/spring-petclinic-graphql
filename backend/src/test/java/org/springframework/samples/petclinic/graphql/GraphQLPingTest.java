package org.springframework.samples.petclinic.graphql;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphQLPingTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void testPing() throws Exception {
        GraphQLResponse response = graphQLTestTemplate.
            withBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNpIiwiaWF0IjoxNjA4ODg5NDQwLCJleHAiOjIzNjYyNzE4NDB9.XG0SEtHiidGuy2A1zy_BfixVMFOv3gGbfwGqEc3F-KU")
            .postForResource("graphql/ping-test.graphql");
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.ping")).isEqualTo("pong");
    }
}
