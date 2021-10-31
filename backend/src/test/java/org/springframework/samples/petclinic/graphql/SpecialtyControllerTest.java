package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureWebGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebGraphQlTester
public class SpecialtyControllerTest {

    @Autowired
    private WebGraphQlTester graphQlTester;

    @Test
    public void specialtiesQueryReturnsList() {
       String query = "query {" +
           "  specialties {" +
           "    id" +
           "    name" +
           "  }" +
           "}";

        this.graphQlTester.query(query)
            .httpHeaders(TestTokens::withUserToken)
            .execute()
            .path("specialties").entityList(Object.class).hasSizeGreaterThan(2);
        ;
    }

    @Test
    public void updateSpecialtyWorks() {
        String query = "mutation {" +
            "  updateSpecialty(input: {specialtyId: 1, name: \"test\"}) {" +
            "    specialty {" +
            "      id" +
            "      name" +
            "    }" +
            "  }" +
            "}";

        this.graphQlTester.query(query)
            .httpHeaders(TestTokens::withUserToken)
            .execute()
            .path("updateSpecialty.specialty.name").entity(String.class).isEqualTo("test");
        ;
    }

    @Test
    public void addSpecialtyWorks() {
        String query = "mutation {" +
            "  addSpecialty(input: {name: \"xxx\"}) {" +
            "    specialty {" +
            "      id" +
            "      name" +
            "    }" +
            "  }" +
            "}";

        this.graphQlTester.query(query)
            .httpHeaders(TestTokens::withUserToken)
            .execute()
            .path("addSpecialty.specialty.name").entity(String.class).isEqualTo("xxx");
        ;
    }

    @Test
    public void addAndRemoveSpecialtyWorks() {
        String getQuery = "query {" +
            "  specialties {" +
            "    id" +
            "    name" +
            "  }" +
            "}";

        final int specialtyCount = this.graphQlTester.query(getQuery)
            .httpHeaders(TestTokens::withUserToken)
            .execute()
            .path("specialties").entityList(Object.class).get().size();

        String query = "mutation {" +
            "  addSpecialty(input: {name: \"yyy\"}) {" +
            "    specialty {" +
            "      id" +
            "      name" +
            "    }" +
            "  }" +
            "}";

        final GraphQlTester.ResponseSpec response = this.graphQlTester.query(query)
            .httpHeaders(TestTokens::withUserToken)
            .execute();
        response
            .path("addSpecialty.specialty.name").entity(String.class).isEqualTo("yyy");
        String id = response.path("addSpecialty.specialty.id").entity(String.class).get();
        Assertions.assertNotNull(id);

        String removeQuery = "mutation {" +
            "  removeSpecialty(input: {specialtyId: " + id + "}) {" +
            "    specialties {" +
            "      id" +
            "    }" +
            "  }" +
            "}"
        ;

        this.graphQlTester.query(removeQuery)
            .httpHeaders(TestTokens::withUserToken)
            .execute()
            .path("removeSpecialty.specialties").entityList(Object.class).hasSize(specialtyCount);
    }


}
