package org.springframework.samples.petclinic.graphql;

//import com.graphql.spring.boot.test.GraphQLResponse;
//import com.graphql.spring.boot.test.GraphQLTestTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * todo: implement tests, they behave differently than described in spring-graphql docs
 */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureGraphQlTester
//@AutoConfigureMockMvc

// https://github.com/spring-projects/spring-graphql/blob/main/samples/webmvc-http/src/test/java/io/spring/sample/graphql/project/MockMvcGraphQlTests.java
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//@AutoConfigureGraphQlTester
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester

public class GraphQLPingTest {
    @Autowired
    private WebGraphQlTester graphQlTester;

    @Test
    public void meReturnsCurrentUser() {
        String query = "query { me { username } } ";
        this.graphQlTester.query(query)
            .headers(headers -> headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNpIiwiaWF0IjoxNjA4ODg5NDQwLCJleHAiOjIzNjYyNzE4NDB9.XG0SEtHiidGuy2A1zy_BfixVMFOv3gGbfwGqEc3F-KU"))
            .execute()
            .path("me.username").entity(String.class).isEqualTo("susi");

    }
//    @Test
//    public void unauthorized() {
//        String query = "query { ping } ";
//        this.graphQlTester.query(query)
//            .execute()
//            .errors()
//            .satisfy(errors -> {
//                assertThat(errors).hasSize(1);
//                assertThat(errors.get(0).getErrorType()).isEqualTo(ErrorType.UNAUTHORIZED);
//            });
//
//    }

}
