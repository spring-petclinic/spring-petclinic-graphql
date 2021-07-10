package org.springframework.samples.petclinic.domain.vet.graphql;

import org.springframework.samples.petclinic.domain.vet.Specialty;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class UpdateSpecialtyPayload {

    private final Specialty specialty;

    public Specialty getSpecialty() {
        return specialty;
    }

    public UpdateSpecialtyPayload(Specialty specialty) {
        this.specialty = specialty;
    }
}
