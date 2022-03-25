package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureHttpGraphQlTester
public class VisitControllerTest {
    @Test
    void shouldAddNewVisit(@Autowired WebGraphQlTester graphQlTester) {
        graphQlTester.mutate()
            .headers(TestTokens::withUserToken)
            .build()
            .documentName("addVisitMutation")
            .execute()
            .path("addVisit.visit.description").entity(String.class).isEqualTo("hurray")
            .path("addVisit.visit.date").entity(String.class).isEqualTo("2020/12/31")
            .path("addVisit.visit.pet.id").entity(String.class).isEqualTo("1");

    }

    @Test
    void shouldAddNewVisitFromVariables_And_HandlesDateCoercingInVariables(@Autowired WebGraphQlTester graphQlTester) {
        graphQlTester.mutate()
            .headers(TestTokens::withUserToken)
            .build()
            .documentName("addVisitMutationWithVariables")
            .variable("addVisitInput", Map.of(
                "petId", 3,//
                "description", "Another visit", //
                "date", "2022/03/25"
            ))
            .execute()
            .path("addVisit.visit.description").entity(String.class).isEqualTo("Another visit")
            .path("addVisit.visit.date").entity(String.class).isEqualTo("2022/03/25")
            .path("addVisit.visit.pet.id").entity(String.class).isEqualTo("3")
            .path("addVisit.visit.treatingVet").valueIsNull();

    }

    @Test
    void shouldAddNewVisitFromVariablesWithVetId(@Autowired WebGraphQlTester graphQlTester) {
        graphQlTester.mutate()
            .headers(TestTokens::withUserToken)
            .build()
            .documentName("addVisitMutationWithVariables")
            .variable("addVisitInput", Map.of(
                "petId", 3,//
                "description", "Another visit", //
                "date", "2022/03/25",
                "vetId", 1
            ))
            .execute()
            .path("addVisit.visit.description").entity(String.class).isEqualTo("Another visit")
            .path("addVisit.visit.date").entity(String.class).isEqualTo("2022/03/25")
            .path("addVisit.visit.pet.id").entity(String.class).isEqualTo("3")
            .path("addVisit.visit.treatingVet.id").entity(String.class).isEqualTo("1");
    }
}
