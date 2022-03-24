package org.springframework.samples.petclinic.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VisitWiring implements RuntimeWiringConfigurer {

    private final PetRepository petRepository;
    private final VetRepository vetRepository;

    public VisitWiring(PetRepository petRepository, VetRepository vetRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Visit", wiring -> wiring
            .dataFetcher("pet", env -> {
                Visit visit = env.getSource();
                Integer petId = visit.getPetId();
                return petRepository.findById(petId);
            })
            .dataFetcher("treatingVet", env -> {
                Visit visit = env.getSource();
                if (!visit.hasVetId()) {
                    return Optional.empty();
                }

                return vetRepository.findById(visit.getVetId());
            })
        );
    }
}
