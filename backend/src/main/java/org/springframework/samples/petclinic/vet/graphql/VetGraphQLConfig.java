package org.springframework.samples.petclinic.vet.graphql;

import graphql.kickstart.tools.SchemaParserDictionary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VetGraphQLConfig {
    @Bean
    SchemaParserDictionary schemaParserDictionary() {
        return new SchemaParserDictionary()
            .add(AddVetSuccessPayload.class)
            .add(AddVetErrorPayload.class);
    }
}
