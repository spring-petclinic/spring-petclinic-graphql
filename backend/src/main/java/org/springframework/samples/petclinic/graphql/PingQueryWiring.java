package org.springframework.samples.petclinic.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

@Component
public class PingQueryWiring implements RuntimeWiringBuilderCustomizer {
    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder.type("Query", typeWiring ->
            typeWiring.dataFetcher("ping", env -> "pong")
        );
    }
}
