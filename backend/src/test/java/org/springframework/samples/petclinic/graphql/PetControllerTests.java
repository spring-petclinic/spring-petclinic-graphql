package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

public class PetControllerTests extends AbstractClinicGraphqlTests {

    @Test
    public void petsQuery_shouldReturnAllPets() {
        userRoleGraphQlTester.document("query { pets { id name } }")
            .execute()
            .path("data.pets[*]").entityList(Object.class).hasSize(13)
            .path("data.pets[0].id").hasValue()
            .path("data.pets[0].name").hasValue();
    }

    @Test
    public void petByIdQuery_shouldReturnPet() {
        userRoleGraphQlTester.document("query { pet(id: 3) { id name } }")
            .execute()
            .path("data.pet.id").entity(String.class).isEqualTo("3")
            .path("data.pet.name").entity(String.class).isEqualTo("Rosy");
    }

    @Test
    public void petByIdQuery_shouldReturnNullForUnknownPet() {
        userRoleGraphQlTester.document("query { pet(id: 666) { id name } }")
            .execute()
            .path("data.pet").valueIsNull();
    }

    @Test
    public void pet_shouldIncludeVisits() {
        userRoleGraphQlTester
            .document("query { pet(id: 8) { id visits { totalCount visits { id  } } } }")
            .execute()
            .path("data.pet.visits.totalCount").entity(int.class).isEqualTo(2)
            .path("data.pet.visits[*]").entityList(Object.class).hasSize(2)
            .path("data.pet.visits.visits[0].id").entity(String.class).isEqualTo("2")
            .path("data.pet.visits.visits[1].id").entity(String.class).isEqualTo("3");
    }

    @Test
    @DirtiesContext
    public void addPetMutation_shouldAddNewPet() {
        userRoleGraphQlTester.documentName("addPetMutation")
            .execute()
            .path("data.addPet.pet.id").hasValue()
            .path("data.addPet.pet.birthDate").entity(String.class).isEqualTo("2019/03/17")
            .path("data.addPet.pet.owner.id").entity(String.class).isEqualTo("2")
            .path("data.addPet.pet.type.id").entity(String.class).isEqualTo("3")
            .path("data.addPet.pet.visits.totalCount").entity(int.class).isEqualTo(0);

        userRoleGraphQlTester.document("query { pets { id } }")
            .execute()
            .path("data.pets[*]").entityList(Object.class).hasSize(14);
    }

    @Test
    @DirtiesContext
    public void updatePetMutation_shouldUpdatePet() {
        userRoleGraphQlTester.documentName("updatePetMutation")
            .variable("updatePetInput", Map.of(
                    "petId", 1, //
                    "birthDate", "2022/03/29" //
                )
            )
            .execute()
            .path("data.updatePet.pet.id").entity(String.class).isEqualTo("1")
            .path("data.updatePet.pet.birthDate").entity(String.class).isEqualTo("2022/03/29")
            .path("data.updatePet.pet.owner.id").entity(String.class).isEqualTo("1")
            .path("data.updatePet.pet.type.id").entity(String.class).isEqualTo("1");


        userRoleGraphQlTester.documentName("updatePetMutation")
            .variable("updatePetInput", Map.of(
                    "petId", 4, //
                    "typeId", 3
                )
            )
            .execute()
            .path("data.updatePet.pet.id").entity(String.class).isEqualTo("4")
            .path("data.updatePet.pet.type.id").entity(String.class).isEqualTo("3");

        userRoleGraphQlTester.documentName("updatePetMutation")
            .variable("updatePetInput", Map.of(
                    "petId", 4, //
                    "typeId", 3
                )
            )
            .execute()
            .path("data.updatePet.pet.id").entity(String.class).isEqualTo("4")
            .path("data.updatePet.pet.type.id").entity(String.class).isEqualTo("3");

        userRoleGraphQlTester.documentName("updatePetMutation")
            .variable("updatePetInput", Map.of(
                    "petId", 5, //
                    "name", "Klaus-Dieter"
                )
            )
            .execute()
            .path("data.updatePet.pet.id").entity(String.class).isEqualTo("5")
            .path("data.updatePet.pet.name").entity(String.class).isEqualTo("Klaus-Dieter");

    }
}
