package org.springframework.samples.petclinic.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

@Component
public class PingQueryWiring implements RuntimeWiringConfigurer {
    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", typeWiring ->
            typeWiring.dataFetcher("ping", env -> "pong")
        );
    }
}
