package org.springframework.samples.petclinic.owner.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
@Component
public class PetMutationResolver implements GraphQLMutationResolver {

    private final static Logger logger = LoggerFactory.getLogger(PetMutationResolver.class);

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final SpecialtyRepository specialtyRepository;

    public PetMutationResolver(OwnerRepository ownerRepository, VisitRepository visitRepository, PetRepository petRepository, PetTypeRepository petTypeRepository, SpecialtyRepository specialtyRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.specialtyRepository = specialtyRepository;
    }

    public AddPetPayload addPet(AddPetInput addPetInput) {

        final Owner owner = ownerRepository.findById(addPetInput.getOwnerId());
        final PetType type = petTypeRepository.findById(addPetInput.getTypeId());

        Pet pet = new Pet();
        pet.setName(addPetInput.getName());
        pet.setType(type);
        pet.setBirthDate(addPetInput.getBirthDate());
        pet.setOwner(owner);
        petRepository.save(pet);

        return new AddPetPayload(pet);
    }

    public UpdatePetPayload updatePet(UpdatePetInput updatePetInput) {
        final Pet pet = petRepository.findById(updatePetInput.getPetId());
        if (updatePetInput.getBirthDate() != null) {
            pet.setBirthDate(updatePetInput.getBirthDate());
        }

        if (updatePetInput.getName() != null) {
            pet.setName(updatePetInput.getName());
        }

        if (updatePetInput.getTypeId() != null) {
            PetType type = petTypeRepository.findById(updatePetInput.getTypeId());
            pet.setType(type);
        }

        petRepository.save(pet);

        return new UpdatePetPayload(pet);
    }

}
