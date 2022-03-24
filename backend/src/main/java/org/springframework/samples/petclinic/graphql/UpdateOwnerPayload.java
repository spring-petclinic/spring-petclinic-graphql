package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Owner;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class UpdateOwnerPayload extends AbstractOwnerPayload {
    public UpdateOwnerPayload(Owner owner) {
        super(owner);
    }
}
