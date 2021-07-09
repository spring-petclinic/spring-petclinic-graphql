package org.springframework.samples.petclinic.graphql;

//import com.graphql.spring.boot.test.GraphQLResponse;
//import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.assertj.core.api.Assertions.assertThat;

/** todo: implement tests, they behave differently than described in spring-graphql docs */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureGraphQlTester
@AutoConfigureMockMvc
public class GraphQLPingTest {

//    @Autowired
//    private org.springframework.graphql.test.tester.GraphQlTester  graphQlTester;

//    @Test
//    public void pingTest() {
//        String query = "query { ping } ";
//    this.graphQlTester.query(query)
//        .execute()
//				.errors()
//				.satisfy(System.out::println);
//
//    }


//    @Autowired
//    private GraphQLTestTemplate graphQLTestTemplate;
//
//    @Test
//    void testPing() throws Exception {
//        GraphQLResponse response = graphQLTestTemplate.
//            withBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNpIiwiaWF0IjoxNjA4ODg5NDQwLCJleHAiOjIzNjYyNzE4NDB9.XG0SEtHiidGuy2A1zy_BfixVMFOv3gGbfwGqEc3F-KU")
//            .postForResource("graphql/ping-test.graphql");
//        assertThat(response.isOk()).isTrue();
//        assertThat(response.get("$.data.ping")).isEqualTo("pong");
//    }
}
