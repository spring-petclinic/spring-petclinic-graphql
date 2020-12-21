package org.springframework.samples.petclinic.owner.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.owner.PetTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for Pet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetTypeQueryResolver implements GraphQLQueryResolver {
    private final PetTypeRepository petTypeRepository;

    public PetTypeQueryResolver(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    public List<PetType> pettypes() {
        return List.copyOf(petTypeRepository.findAll());
    }
}
