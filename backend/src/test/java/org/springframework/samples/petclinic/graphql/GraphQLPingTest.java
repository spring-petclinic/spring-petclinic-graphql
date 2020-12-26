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
            withBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYwODg4OTQ0MCwiZXhwIjoyMzY2MjcxODQwfQ.sKZuQmhZOghkb9l4p4oTIBnlP_ef5D7J0CLZz7Ac5o4")
            .postForResource("graphql/ping-test.graphql");
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.ping")).isEqualTo("pong");
    }
}
