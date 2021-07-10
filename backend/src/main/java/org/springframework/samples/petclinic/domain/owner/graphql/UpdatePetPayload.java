package org.springframework.samples.petclinic.domain.owner.graphql;

import org.springframework.samples.petclinic.domain.owner.Pet;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class UpdatePetPayload extends AbstractPetPayload {
    public UpdatePetPayload(Pet pet) {
        super(pet);
    }
}
