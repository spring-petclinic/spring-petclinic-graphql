package org.springframework.samples.petclinic.domain.vet.graphql;

import org.springframework.samples.petclinic.domain.vet.Specialty;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddSpecialtyPayload {

    private final Specialty specialty;

    public Specialty getSpecialty() {
        return specialty;
    }

    public AddSpecialtyPayload(Specialty specialty) {
        this.specialty = specialty;
    }
}
