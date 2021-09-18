package org.springframework.samples.petclinic.domain.vet.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.domain.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.domain.vet.VetRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for PetClinic Vet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class VetQueryWiring implements RuntimeWiringConfigurer {
    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;

    public VetQueryWiring(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", wiring -> wiring
            .dataFetcher("vets", env -> List.copyOf(vetRepository.findAll()))
            .dataFetcher("vet", env -> {
                int id = env.getArgument("id");
                return vetRepository.findById(id);
            })
            .dataFetcher("specialties", env -> List.copyOf(specialtyRepository.findAll()))
        );
    }
}
