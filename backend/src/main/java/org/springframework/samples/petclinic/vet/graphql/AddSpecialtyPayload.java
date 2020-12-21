package org.springframework.samples.petclinic.vet.graphql;

import org.springframework.samples.petclinic.vet.Specialty;

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
