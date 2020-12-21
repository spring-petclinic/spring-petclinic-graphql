package org.springframework.samples.petclinic.graphql.types;

import org.springframework.samples.petclinic.vet.Specialty;

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
