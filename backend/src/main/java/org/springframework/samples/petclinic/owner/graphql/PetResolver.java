package org.springframework.samples.petclinic.owner.graphql;

import graphql.kickstart.tools.GraphQLResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.samples.petclinic.visit.graphql.VisitConnection;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetResolver implements GraphQLResolver<Pet> {
    private final VisitRepository visitRepository;

    public PetResolver(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public VisitConnection visits(Pet pet) {
        var visits = visitRepository.findByPetId(pet.getId());
        return new VisitConnection(visits);
    }
}
