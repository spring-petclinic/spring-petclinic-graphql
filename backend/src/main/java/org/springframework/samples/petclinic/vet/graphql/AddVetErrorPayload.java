package org.springframework.samples.petclinic.vet.graphql;

public class AddVetErrorPayload implements AddVetPayload {
    private final String error;

    public AddVetErrorPayload(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
