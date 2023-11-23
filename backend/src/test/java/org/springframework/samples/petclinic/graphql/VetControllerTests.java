package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.graphql.execution.ErrorType;

import static org.assertj.core.api.Assertions.assertThat;

public class VetControllerTests extends AbstractClinicGraphqlTests{

    /**
     * EXAMPLE:
     * --------------------------
     *
     * Mutation returning a union type (AddVetPayload) with data or error, returns data if invoked correctly
     */
    @Test
    void shouldAddNewVet() {
        managerRoleGraphQlTester
            .documentName("addVetMutation")
            .variable("specialtyIds", new int[]{1, 3})
            .execute()
            .path("addVet.vet.id").hasValue()
            .path("addVet.vet.firstName").entity(String.class).isEqualTo("Klaus")
            .path("addVet.vet.lastName").entity(String.class).isEqualTo("Smith")
            .path("addVet.vet.specialties[*]").entityList(Object.class).hasSize(2)
            .path("addVet.vet.specialties[0].id").entity(String.class).isEqualTo("3")
            .path("addVet.vet.specialties[1].id").entity(String.class).isEqualTo("1");
    }

    /**
     * EXAMPLE:
     * --------------------------
     *
     * Mutation returning a union type (AddVetPayload) with data or error, returns "domain" error
     * type if invoked correctly
     */
    @Test
    void shouldReturnErrorPayloadOnUnknownSpecialty() {
        managerRoleGraphQlTester
            .documentName("addVetMutation")
            .variable("specialtyIds", new int[]{666})
            .execute()
            .path("addVet.vet").pathDoesNotExist()
            .path("addVet.error").entity(String.class).isEqualTo("Specialty with Id '666' not found");
    }

    /**
     * EXAMPLE:
     * --------------------------
     *
     * Mutation is secured using fine-grained security with @PreAuth
     */
    @Test
    void shouldForbidAddingVetsAsUser() {
        userRoleGraphQlTester
            .documentName("addVetMutation")
            .variable("specialtyIds", new int[]{1, 3})
            .execute()
            .errors()
            .satisfy(errors -> {
                assertThat(errors).hasSize(1);
                assertThat(errors.get(0).getErrorType()).isEqualTo(ErrorType.FORBIDDEN);
            });
    }


    @Test
    public void vetsReturnsListOfAllVets() {
        // language=GraphQL
        String query = """

            query {
              vets {
                edges {
                  node { id lastName firstName visits { visits { id pet { id } } } }
                  cursor
                }
                pageInfo { hasNextPage }
              }
            }
        """
        ;

        userRoleGraphQlTester.document(query)
            .execute()
            .path("vets.edges").entityList(Object.class).hasSize(10)
            .path("vets.edges[0].node.id").entity(int.class).isEqualTo(1)
            .path("vets.edges[1].node.id").entity(int.class).isEqualTo(3)
            .path("vets.edges[1].node.lastName").entity(String.class).isEqualTo("Douglas")
            .path("vets.edges[5].node.id").entity(int.class).isEqualTo(4)
            .path("vets.edges[5].node.visits.visits").entityList(Object.class).hasSize(2)
            .path("vets.edges[5].node.visits.visits[0].id").entity(int.class).isEqualTo(1)
            .path("vets.edges[5].node.visits.visits[0].pet.id").entity(int.class).isEqualTo(7)
            .path("vets.edges[5].node.visits.visits[1].id").entity(int.class).isEqualTo(3)
            .path("vets.edges[5].node.visits.visits[1].pet.id").entity(int.class).isEqualTo(8)
            .path("vets.edges[8].node.id").entity(int.class).isEqualTo(5)
            .path("vets.edges[9].node.id").entity(int.class).isEqualTo(9)
            .path("vets.pageInfo.hasNextPage").entity(boolean.class).isEqualTo(false)
        ;
    }

    @Test
    public void vetReturnsVetById() {
        String query = "query {" +
            "  vet(id:4) {" +
            "    id" +
            "    specialties {" +
            "      id" +
            "    }"+
            "    visits {" +
            "      totalCount" +
            "      visits {" +
            "        id" +
            "        pet {" +
            "          id" +
            "        }" +
            "      }" +
            "    }" +
            "  }" +
            "}";

        userRoleGraphQlTester.document(query)
            .execute()
            .path("vet.specialties[0].id").entity(int.class).isEqualTo(2)
            .path("vet.visits.totalCount").entity(int.class).isEqualTo(2)
            .path("vet.visits.visits[0].id").entity(String.class).isEqualTo("1")
            .path("vet.visits.visits[0].pet.id").entity(String.class).isEqualTo("7")
        ;
    }

    @Test
    public void vetReturnsNullIfNotFound() {
        String query = "query {" +
            "  vet(id:666) {" +
            "    id" +
            "    specialties {" +
            "      id" +
            "    }"+
            "    visits {" +
            "      totalCount" +
            "      visits {" +
            "        id" +
            "        pet {" +
            "          id" +
            "        }" +
            "      }" +
            "    }" +
            "  }" +
            "}";

        userRoleGraphQlTester.document(query)
            .execute()
            .path("vet").valueIsNull();
        ;
    }

}
