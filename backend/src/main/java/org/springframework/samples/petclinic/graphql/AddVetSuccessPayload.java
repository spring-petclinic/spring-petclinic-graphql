package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Vet;

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
