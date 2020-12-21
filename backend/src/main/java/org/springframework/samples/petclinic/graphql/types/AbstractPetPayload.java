package org.springframework.samples.petclinic.graphql.types;

import org.springframework.samples.petclinic.owner.Pet;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AbstractPetPayload {

    private final Pet pet;

    public AbstractPetPayload(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }
}
