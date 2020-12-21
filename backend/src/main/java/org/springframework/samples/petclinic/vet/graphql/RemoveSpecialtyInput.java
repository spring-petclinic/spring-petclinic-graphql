package org.springframework.samples.petclinic.vet.graphql;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class RemoveSpecialtyInput {

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    private int specialtyId;
}
