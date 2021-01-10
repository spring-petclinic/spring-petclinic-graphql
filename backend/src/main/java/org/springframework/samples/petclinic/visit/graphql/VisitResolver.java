package org.springframework.samples.petclinic.visit.graphql;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VisitResolver implements GraphQLResolver<Visit> {

    private final PetRepository petRepository;
    private final VetRepository vetRepository;

    public VisitResolver(PetRepository petRepository, VetRepository vetRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
    }

    public Pet getPet(Visit visit) {
        Integer petId = visit.getPetId();

        return petRepository.findById(petId);
    }

    public Optional<Vet> getTreatingVet(Visit visit) {
        if (!visit.hasVetId()) {
            return Optional.empty();
        }

        return vetRepository.findById(visit.getVetId());
    }
}
