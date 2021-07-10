package org.springframework.samples.petclinic.domain.owner.graphql;

import org.springframework.samples.petclinic.domain.owner.Owner;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class UpdateOwnerPayload extends AbstractOwnerPayload {
    public UpdateOwnerPayload(Owner owner) {
        super(owner);
    }
}
