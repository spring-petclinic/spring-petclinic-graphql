package org.springframework.samples.petclinic.owner.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for Pet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetQueryResolver implements GraphQLQueryResolver {
    private final PetRepository petRepository;

    public PetQueryResolver(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet pet(int id) {
        return petRepository.findById(id);
    }

    public List<Pet> pets() {
        return List.copyOf(petRepository.findAll());
    }
}
