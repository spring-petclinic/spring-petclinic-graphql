package org.springframework.samples.petclinic.vet.graphql;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class UpdateSpecialtyInput {

    private int specialtyId;
    private String name;

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
