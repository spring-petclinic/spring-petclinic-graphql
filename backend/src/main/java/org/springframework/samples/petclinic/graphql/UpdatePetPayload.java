package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Pet;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public record UpdatePetPayload (Pet pet) {}
