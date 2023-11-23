package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.graphql.execution.ErrorType;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerControllerTests extends AbstractClinicGraphqlTests{

    @Test
    public void owners_no_sort_order() {
        // language=GraphQL
        var query = """
            query {
                owners(first: 5) { edges { node { id } } }
            }
            """;

        // by default results are orderd by id
        userRoleGraphQlTester.document(query)
            .execute()
            .path("owners.edges").entityList(Object.class).hasSize(5)
            .path("owners.edges[0].node.id").entity(Integer.class).isEqualTo(1)
            .path("owners.edges[1].node.id").entity(Integer.class).isEqualTo(2)
            .path("owners.edges[2].node.id").entity(Integer.class).isEqualTo(3)
            .path("owners.edges[3].node.id").entity(Integer.class).isEqualTo(4)
            .path("owners.edges[4].node.id").entity(Integer.class).isEqualTo(5)
        ;
    }

    @Test
    public void owners_after() {
        // language=GraphQL
        var query = """
            query {
                owners(first: 5, after:"T18z") { edges { node { id } } }
            }
            """;

        // by default results are orderd by id
        userRoleGraphQlTester.document(query)
            .execute()
            .path("owners.edges").entityList(Object.class).hasSize(5)
            .path("owners.edges[0].node.id").entity(Integer.class).isEqualTo(4)
            .path("owners.edges[1].node.id").entity(Integer.class).isEqualTo(5)
            .path("owners.edges[2].node.id").entity(Integer.class).isEqualTo(6)
            .path("owners.edges[3].node.id").entity(Integer.class).isEqualTo(7)
            .path("owners.edges[4].node.id").entity(Integer.class).isEqualTo(8)
        ;
    }

    @Test
    public void owners_order_by_lastname() {
        // language=GraphQL
        var query = """
            query {
                owners(first: 3,
                       after: "T180",
                       order: [{field: lastName}])
                       { edges { node { id lastName firstName } } }
            }
            """;

        userRoleGraphQlTester.document(query)
            .execute()
            .path("owners.edges").entityList(Object.class).hasSize(3)
            .path("owners.edges[0].node.id").entity(Integer.class).isEqualTo(6)
            .path("owners.edges[1].node.id").entity(Integer.class).isEqualTo(17)
            .path("owners.edges[2].node.id").entity(Integer.class).isEqualTo(2)
        ;
    }

    @Test
    public void owners_order_by_lastname_and_firstname() {
        // language=GraphQL
        var query = """
            query {
                owners(first: 3,
                       after: "T180",
                       order: [{field: lastName}, {field:firstName, direction:DESC}])
                       { edges { node { id lastName firstName } } }
            }
            """;

        userRoleGraphQlTester.document(query)
            .execute()
            .path("owners.edges").entityList(Object.class).hasSize(3)
            .path("owners.edges[0].node.id").entity(Integer.class).isEqualTo(6)
            .path("owners.edges[1].node.id").entity(Integer.class).isEqualTo(17)
            .path("owners.edges[2].node.id").entity(Integer.class).isEqualTo(4)
        ;
    }

    @Test
    public void owners_order_by_and_filter() {
        // language=GraphQL
        var query = """
            query {
                owners(first: 10, order:
                       [{field: lastName}]
                       filter: {lastName: "du"},
                       after: "T18x")
                       { edges { node { id lastName firstName } } }
            }
            """;

        userRoleGraphQlTester.document(query)
            .execute()
            .path("owners.edges").entityList(Object.class).hasSize(2)
            .path("owners.edges[0].node.id").entity(Integer.class).isEqualTo(30)
            .path("owners.edges[1].node.id").entity(Integer.class).isEqualTo(21)
        ;
    }

    @Test
    public void owners_filter() {
        // language=GraphQL
        var query = """
            query {
                owners(first: 10,
                       filter: {lastName: "du"},
                       after: "T18x")
                       { edges { node { id lastName firstName } } }
            }
            """;

        userRoleGraphQlTester.document(query)
            .execute()
            .path("owners.edges").entityList(Object.class).hasSize(2)
            .path("owners.edges[0].node.id").entity(Integer.class).isEqualTo(21)
            .path("owners.edges[1].node.id").entity(Integer.class).isEqualTo(30)
        ;
    }




}
