package org.springframework.samples.petclinic.domain.vet.graphql;

import org.springframework.samples.petclinic.domain.vet.Vet;

/**
 * @author Nils Hartmann
 */
public class AddVetSuccessPayload implements AddVetPayload {
    private final Vet vet;

    public AddVetSuccessPayload(Vet vet) {
        this.vet = vet;
    }

    public Vet getVet() {
        return vet;
    }
}
