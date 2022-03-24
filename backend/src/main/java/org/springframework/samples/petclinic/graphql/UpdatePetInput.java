package org.springframework.samples.petclinic.graphql;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class UpdatePetInput extends AbstractPetInput {

    private int petId;

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }
}
