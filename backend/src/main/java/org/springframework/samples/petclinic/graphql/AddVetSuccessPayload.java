package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Vet;

/**
 * @author Nils Hartmann
 */
public record AddVetSuccessPayload(Vet vet) implements AddVetPayload {
}
