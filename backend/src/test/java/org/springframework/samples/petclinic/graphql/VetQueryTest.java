package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.WebGraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureHttpGraphQlTester
public class VetQueryTest {

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
    public void vetsReturnsListOfAllVets() {
       String query = "query {" +
           "  vets {" +
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

        this.graphQlTesterWithHeader.document(query)
            .execute()
            .path("vets").entityList(Object.class).hasSizeGreaterThan(2)
            .path("vets[2].specialties").entityList(Object.class).hasSize(2)
            .path("vets[0].visits.totalCount").entity(int.class).isEqualTo(20)
            .path("vets[0].visits.visits[0].id").entity(String.class).isEqualTo("1")
            .path("vets[0].visits.visits[0].pet.id").entity(String.class).isEqualTo("1")
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

        this.graphQlTesterWithHeader.document(query)
            .execute()
            .path("vet.specialties[0].id").entity(int.class).isEqualTo(0)
            .path("vet.visits.totalCount").entity(int.class).isEqualTo(19)
            .path("vet.visits.visits[0].id").entity(String.class).isEqualTo("9")
            .path("vet.visits.visits[0].pet.id").entity(String.class).isEqualTo("9")
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

        this.graphQlTesterWithHeader.document(query)
            .execute()
            .path("vet").valueIsNull();
        ;
    }

}
