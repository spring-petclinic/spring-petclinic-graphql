package org.springframework.samples.petclinic.domain.vet.graphql;

import org.springframework.samples.petclinic.domain.vet.Specialty;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class RemoveSpecialtyPayload {

    private final List<Specialty> specialties;

    public RemoveSpecialtyPayload(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }
}
