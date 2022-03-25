package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.WebGraphQlTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureHttpGraphQlTester
public class AddVetMutationTest {

    @Test
    void shouldAddNewVet(@Autowired WebGraphQlTester graphQlTester) {
        graphQlTester.mutate()
            .headers(TestTokens::withManagerToken)
            .build()
            .documentName("addVetMutation")
            .variable("specialtyIds",new int[]{1,3})
            .execute()
            .path("addVet.vet.id").hasValue()
            .path("addVet.vet.firstName").entity(String.class).isEqualTo("Klaus")
            .path("addVet.vet.lastName").entity(String.class).isEqualTo("Smith")
            .path("addVet.vet.specialties[*]").entityList(Object.class).hasSize(2)
            .path("addVet.vet.specialties[0].id").entity(String.class).isEqualTo("3")
            .path("addVet.vet.specialties[1].id").entity(String.class).isEqualTo("1");

    }

    @Test
    void shouldReturnErrorPayloadOnUnknownSpecialty(@Autowired WebGraphQlTester graphQlTester) {
        graphQlTester.mutate()
            .headers(TestTokens::withManagerToken)
            .build()
            .documentName("addVetMutation")
            .variable("specialtyIds",new int[]{666})
            .execute()
            .path("addVet.vet").pathDoesNotExist()
            .path("addVet.error").entity(String.class).isEqualTo("Specialty with Id '666' not found");
    }

    @Test
    void shouldForbidAddingVetsAsUser(@Autowired WebGraphQlTester graphQlTester) {
        graphQlTester.mutate()
            .headers(TestTokens::withUserToken)
            .build()
            .documentName("addVetMutation")
            .variable("specialtyIds",new int[]{1,3})
            .execute()
            .errors()
            .satisfy(errors -> {
                assertThat(errors).hasSize(1);
                assertThat(errors.get(0).getErrorType()).isEqualTo(ErrorType.FORBIDDEN);
            });
    }



}
