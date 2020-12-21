package org.springframework.samples.petclinic.graphql.resolvers;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitResolver implements GraphQLResolver<Visit> {

    private final PetRepository petRepository;

    public VisitResolver(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet getPet(Visit visit) {
        Integer petId = visit.getPetId();

        return petRepository.findById(petId);
    }
}
