package org.springframework.samples.petclinic.domain.owner.graphql;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddPetInput extends AbstractPetInput {

    private Integer ownerId;

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "AddPetInput{" +
            "ownerId=" + ownerId +
            "} " + super.toString();
    }
}
