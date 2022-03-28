package org.springframework.samples.petclinic.model;

import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Validated
public class PetService {
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;

    public PetService(OwnerRepository ownerRepository, PetRepository petRepository, PetTypeRepository petTypeRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
    }

    @Transactional
    public Pet addPet(int ownerId, int petTypeId, @NotEmpty String petName, @NotNull LocalDate petBirthData) {
        final Owner owner = ownerRepository.findById(ownerId);
        final PetType type = petTypeRepository.findById(petTypeId);

        Pet pet = new Pet();
        pet.setName(petName);
        pet.setType(type);
        pet.setBirthDate(petBirthData);
        pet.setOwner(owner);

        petRepository.save(pet);

        return pet;
    }

    @Transactional
    public Pet updatePet(int petId, Optional<Integer> petTypeId, String petName, LocalDate petBirthData) {
        final Pet pet = petRepository.findById(petId);

        setIfGiven(petBirthData, pet::setBirthDate);
        setIfGiven(petName, pet::setName);
        setIfGiven(petTypeId.map(petTypeRepository::findById).orElse(null), pet::setType);

        petRepository.save(pet);

        return pet;
    }

    private <T> void setIfGiven(T value, Consumer<T> s) {
        if (value != null) {
            s.accept(value);
        }
    }
}
