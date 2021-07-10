package org.springframework.samples.petclinic.domain.owner.graphql;

import org.springframework.samples.petclinic.domain.owner.Owner;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddOwnerPayload extends AbstractOwnerPayload {

    public AddOwnerPayload(Owner owner) {
        super(owner);
    }
}
