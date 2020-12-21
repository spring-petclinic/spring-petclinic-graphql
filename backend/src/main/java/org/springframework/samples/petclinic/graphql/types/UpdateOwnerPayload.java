package org.springframework.samples.petclinic.graphql.types;

import org.springframework.samples.petclinic.owner.Owner;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class UpdateOwnerPayload extends AbstractOwnerPayload {
    public UpdateOwnerPayload(Owner owner) {
        super(owner);
    }
}
