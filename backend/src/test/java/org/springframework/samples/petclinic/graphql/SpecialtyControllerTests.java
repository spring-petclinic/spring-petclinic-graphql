package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.annotation.DirtiesContext;

public class SpecialtyControllerTests extends AbstractClinicGraphqlTests {

    @Test
    public void specialtiesQueryReturnsList() {
        String query = "query {" +
            "  specialties {" +
            "    id" +
            "    name" +
            "  }" +
            "}";

        userRoleGraphQlTester
            .document(query)
            .execute()
            .path("specialties").entityList(Object.class).hasSizeGreaterThan(2);
        ;
    }

    @Test
    @DirtiesContext
    public void updateSpecialtyWorks() {
        String query = "mutation {" +
            "  updateSpecialty(input: {specialtyId: 1, name: \"test\"}) {" +
            "    specialty {" +
            "      id" +
            "      name" +
            "    }" +
            "  }" +
            "}";

        userRoleGraphQlTester
            .document(query)
            .execute()
            .path("updateSpecialty.specialty.name").entity(String.class).isEqualTo("test");
        ;
    }

    @Test
    @DirtiesContext
    public void addSpecialtyWorks() {
        String query = "mutation {" +
            "  addSpecialty(input: {name: \"xxx\"}) {" +
            "    specialty {" +
            "      id" +
            "      name" +
            "    }" +
            "  }" +
            "}";

        userRoleGraphQlTester
            .document(query)
            .execute()
            .path("addSpecialty.specialty.name").entity(String.class).isEqualTo("xxx");
        ;
    }

    @Test
    @DirtiesContext
    public void addAndRemoveSpecialtyWorks() {
        String getQuery = "query {" +
            "  specialties {" +
            "    id" +
            "    name" +
            "  }" +
            "}";

        final int specialtyCount = userRoleGraphQlTester
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

        GraphQlTester.Response response = userRoleGraphQlTester
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
            "}";

        this.userRoleGraphQlTester.document(removeQuery)
            .execute()
            .path("removeSpecialty.specialties").entityList(Object.class).hasSize(specialtyCount);
    }


}
