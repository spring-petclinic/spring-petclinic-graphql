package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;

public class PetTypeControllerTests extends AbstractClinicGraphqlTests {

    @Test
    void pettypesQuery_shouldReturnAllPetTypes() {
        this.userRoleGraphQlTester.document("query { pettypes { id name } }")
            .execute()
            .path("data.pettypes[*]").entityList(Object.class).hasSize(6)
            .path("data.pettypes[1].id").hasValue()
            .path("data.pettypes[1].name").hasValue();
    }

}
