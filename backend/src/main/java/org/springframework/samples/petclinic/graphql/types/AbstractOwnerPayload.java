package org.springframework.samples.petclinic.graphql.types;

import org.springframework.samples.petclinic.owner.Owner;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AbstractOwnerPayload {

    private final Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public AbstractOwnerPayload(Owner owner) {
        this.owner = owner;
    }
}
