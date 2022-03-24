package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureHttpGraphQlTester
public class SpecialtyControllerTest {

    @Autowired
    private WebGraphQlTester graphQlTester;

    private WebGraphQlTester graphQlTesterWithHeader;

    @BeforeEach
    void setupGraphQlTesterWithHeader() {
        graphQlTesterWithHeader = graphQlTester.mutate()
            .headers(TestTokens::withUserToken)
            .build();
    }

    @Test
    public void specialtiesQueryReturnsList() {
       String query = "query {" +
           "  specialties {" +
           "    id" +
           "    name" +
           "  }" +
           "}";

        this.graphQlTesterWithHeader
            .document(query)
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

        this.graphQlTesterWithHeader
            .document(query)
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

        this.graphQlTesterWithHeader
            .document(query)
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

        final int specialtyCount = this.graphQlTesterWithHeader
            .document(getQuery)
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

        GraphQlTester.Response response = this.graphQlTesterWithHeader
            .document(query)
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

        this.graphQlTesterWithHeader.document(removeQuery)
            .execute()
            .path("removeSpecialty.specialties").entityList(Object.class).hasSize(specialtyCount);
    }


}
