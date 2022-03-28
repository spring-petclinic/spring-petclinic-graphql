package org.springframework.samples.petclinic.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetService;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Controller;

import java.util.Optional;

/**
 * GraphQL handler functions for Pet GraphQL type, Query and Mutation
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class PetController {

    private final PetService petService;
    private final PetRepository petRepository;
    private final VisitRepository visitRepository;

    public PetController(PetService petService, PetRepository petRepository, VisitRepository visitRepository) {
        this.petService = petService;
        this.petRepository = petRepository;
        this.visitRepository = visitRepository;
    }

    @QueryMapping
    public Iterable<Pet> pets() {
        return petRepository.findAll();
    }

    @QueryMapping
    public Pet pet(@Argument Integer id) {
        return petRepository.findById(id);
    }

    @SchemaMapping
    public VisitConnection visits(Pet pet) {
        var visits = visitRepository.findByPetIdOrderById(pet.getId());
        return new VisitConnection(visits);
    }

    @MutationMapping
    public AddPetPayload addPet(@Argument AddPetInput input) {
        Pet pet = petService.addPet(
            input.getOwnerId(),
            input.getTypeId(),
            input.getName(),
            input.getBirthDate()
        );

        return new AddPetPayload(pet);
    }

    @MutationMapping
    public UpdatePetPayload updatePet(@Argument UpdatePetInput input) {
        Pet pet = petService.updatePet(
            input.getPetId(),
            Optional.ofNullable(input.getTypeId()),
            input.getName(),
            input.getBirthDate()
        );

        return new UpdatePetPayload(pet);
    }
}
