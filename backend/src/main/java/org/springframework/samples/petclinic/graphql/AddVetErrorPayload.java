package org.springframework.samples.petclinic.graphql;

public record AddVetErrorPayload(String error) implements AddVetPayload {
}
