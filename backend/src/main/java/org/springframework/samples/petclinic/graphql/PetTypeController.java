package org.springframework.samples.petclinic.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Resolver for Pet Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class PetTypeController {
    private final PetTypeRepository petTypeRepository;

    public PetTypeController(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @QueryMapping
    public Iterable<PetType> pettypes() {
        return petTypeRepository.findAll();
    }
}
