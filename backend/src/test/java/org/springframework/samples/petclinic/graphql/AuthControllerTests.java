package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class AuthControllerTests extends AbstractClinicGraphqlTests {
    @Test
    void shouldReturnCurrentUser() {
        userRoleGraphQlTester
            .documentName("meQuery")
            .execute()
            .path("me.username").entity(String.class).isEqualTo("joe")
            .path("me.fullname").entity(String.class).isEqualTo("Joe Hill");
    }

    @Test
    void shouldReturnUnauthorizedWithoutToken() {

        assertThatThrownBy(() ->
            unauthorizedGraphqlTester.mutate()
                .build()
                .documentName("meQuery")
                .executeAndVerify())
            .hasMessage("Status expected:<200 OK> but was:<401 UNAUTHORIZED>");
    }

}
