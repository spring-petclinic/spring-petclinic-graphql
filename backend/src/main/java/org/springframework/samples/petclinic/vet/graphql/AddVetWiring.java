package org.springframework.samples.petclinic.vet.graphql;


import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.vet.Vet;

@Configuration
public class AddVetWiring implements RuntimeWiringBuilderCustomizer {
    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder
            .type("AddVetPayload", typeBuilder -> typeBuilder.typeResolver(env -> {
                Object javaObject = env.getObject();
                if (javaObject instanceof AddVetSuccessPayload) {
                    return env.getSchema().getObjectType("AddVetSuccessPayload");
                }
                return env.getSchema().getObjectType("AddVetErrorPayload");
            }))
            .type("Person", typeBuilder -> typeBuilder.typeResolver(env -> {
                Object javaObject = env.getObject();
                if (javaObject instanceof Vet) {
                    return env.getSchema().getObjectType("Vet");
                }
                return env.getSchema().getObjectType("Owner");
            }));
    }
}
