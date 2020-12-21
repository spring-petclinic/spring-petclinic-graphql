package org.springframework.samples.petclinic.owner.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.vet.graphql.SpecialtyMutationResolver;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetTypeRepository;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
@Component
public class OwnerMutationResolver implements GraphQLMutationResolver {
    private final static Logger logger = LoggerFactory.getLogger(OwnerMutationResolver.class);

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final SpecialtyRepository specialtyRepository;

    public OwnerMutationResolver(OwnerRepository ownerRepository, VisitRepository visitRepository, PetRepository petRepository, PetTypeRepository petTypeRepository, SpecialtyRepository specialtyRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.specialtyRepository = specialtyRepository;
    }

    public AddOwnerPayload addOwner(AddOwnerInput addOwnerInput) {
        final Owner owner = new Owner();
        owner.setAddress(addOwnerInput.getAddress());
        owner.setCity(addOwnerInput.getCity());
        owner.setTelephone(addOwnerInput.getTelephone());
        owner.setFirstName(addOwnerInput.getFirstName());
        owner.setLastName(addOwnerInput.getLastName());

        ownerRepository.save(owner);

        return new AddOwnerPayload(owner);
    }

    public UpdateOwnerPayload updateOwner(UpdateOwnerInput updateOwnerInput) {
        Owner owner = ownerRepository.findById(updateOwnerInput.getOwnerId());
        if (updateOwnerInput.getAddress() != null) {
            owner.setAddress(updateOwnerInput.getAddress());
        }

        if (updateOwnerInput.getFirstName() != null) {
            owner.setFirstName(updateOwnerInput.getFirstName());
        }

        if (updateOwnerInput.getLastName() != null) {
            owner.setLastName(updateOwnerInput.getLastName());
        }

        if (updateOwnerInput.getCity() != null) {
            owner.setCity(updateOwnerInput.getCity());
        }

        if (updateOwnerInput.getTelephone() != null) {
            owner.setTelephone(updateOwnerInput.getTelephone());
        }

        ownerRepository.save(owner);

        return new UpdateOwnerPayload(owner);
    }

}
