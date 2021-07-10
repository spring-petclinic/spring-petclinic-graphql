package org.springframework.samples.petclinic.domain.owner.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.domain.owner.PetTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for Pet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetTypeQueryWiring implements RuntimeWiringBuilderCustomizer {
    private final PetTypeRepository petTypeRepository;

    public PetTypeQueryWiring(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder.type("Query", wiring -> wiring
            .dataFetcher("pettypes", env -> List.copyOf(petTypeRepository.findAll()))
        );
    }

}
