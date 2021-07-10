package org.springframework.samples.petclinic.domain.owner.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.domain.owner.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for Pet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetQueryResolver implements RuntimeWiringBuilderCustomizer {
    private final PetRepository petRepository;

    public PetQueryResolver(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder.type("Query", wiring -> wiring
            .dataFetcher("pet", env -> {
                int id = env.getArgument("id");
                return petRepository.findById(id);
            })
            .dataFetcher("pets", env -> List.copyOf(petRepository.findAll()))
        );
    }
}
