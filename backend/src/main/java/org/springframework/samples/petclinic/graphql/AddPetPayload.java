package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Pet;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddPetPayload extends AbstractPetPayload {

    public AddPetPayload(Pet pet) {
        super(pet);
    }
}
