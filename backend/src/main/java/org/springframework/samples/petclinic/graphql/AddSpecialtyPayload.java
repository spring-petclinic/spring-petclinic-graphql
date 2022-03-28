package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Specialty;

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
