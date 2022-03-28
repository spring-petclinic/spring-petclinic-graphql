package org.springframework.samples.petclinic.graphql.runtime;

import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.model.Vet;

/**
 * GraphQL Runtime Wiring Configuration
 *
 * RunimeWiring connects the schema with behviour. Most of it is done
 * aumatically by graphql-java, spring-graphql and Spring Boot starter for spring-graphql
 * but here we do our own customizations
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
public class PetClinicRuntimeWiringConfiguration {

    @Bean
    RuntimeWiringConfigurer petclinicWiringConfigurer() {
        return builder -> {
            addDateCoercing(builder);
            addPersonTypeResolver(builder);
        };
    }

    private void addPersonTypeResolver(RuntimeWiring.Builder builder) {
        builder.type("Person", typeBuilder -> typeBuilder.typeResolver(env -> {
            Object javaObject = env.getObject();
            if (javaObject instanceof Vet) {
                return env.getSchema().getObjectType("Vet");
            }
            return env.getSchema().getObjectType("Owner");
        }));
    }

    private void addDateCoercing(RuntimeWiring.Builder builder) {
        builder.scalar(GraphQLScalarType.newScalar()
            .name("Date")
            .description("A Type representing a date (without time, only a day)")
            .coercing(new DateCoercing())
            .build());
    }


}
