package org.springframework.samples.petclinic.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for Pet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetTypeQueryWiring implements RuntimeWiringConfigurer {
    private final PetTypeRepository petTypeRepository;

    public PetTypeQueryWiring(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", wiring -> wiring
            .dataFetcher("pettypes", env -> List.copyOf(petTypeRepository.findAll()))
        );
    }

}
