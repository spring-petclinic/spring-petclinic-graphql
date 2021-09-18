package org.springframework.samples.petclinic.graphql;

import org.springframework.http.HttpHeaders;

import java.util.function.Consumer;

public class TestTokens {

    public static void withManagerToken(HttpHeaders headers) {
        headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXNpIiwiaWF0IjoxNjA4ODg5NDQwLCJleHAiOjIzNjYyNzE4NDB9.XG0SEtHiidGuy2A1zy_BfixVMFOv3gGbfwGqEc3F-KU");
    }

    public static void withUserToken(HttpHeaders headers) {
        headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2UiLCJpYXQiOjE2MDg4ODk0NDAsImV4cCI6MjM2NjI3MTg0MH0.V36ynhDffqb9LQFsckOdk6lFhcVEDhOCFxFCQDAYG0o");
    }

}
