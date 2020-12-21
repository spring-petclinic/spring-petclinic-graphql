package org.springframework.samples.petclinic.visit.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetTypeRepository;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
@Component
public class VisitMutationResolver implements GraphQLMutationResolver {
    private final static Logger logger = LoggerFactory.getLogger(VisitMutationResolver.class);

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final SpecialtyRepository specialtyRepository;

    public VisitMutationResolver(OwnerRepository ownerRepository, VisitRepository visitRepository, PetRepository petRepository, PetTypeRepository petTypeRepository, SpecialtyRepository specialtyRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.specialtyRepository = specialtyRepository;
    }

    public AddVisitPayload addVisit(AddVisitInput addVisitInput) {
        Pet pet = petRepository.findById(addVisitInput.getPetId());

        Visit visit = new Visit();
        visit.setDescription(addVisitInput.getDescription());
        visit.setPetId(pet.getId());
        visit.setDate(addVisitInput.getDate());

        visitRepository.save(visit);

        return new AddVisitPayload(visit);
    }

}
