package org.springframework.samples.petclinic.graphql.types;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class UpdateOwnerInput extends AbstractOwnerInput {

    private int ownerId;

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
