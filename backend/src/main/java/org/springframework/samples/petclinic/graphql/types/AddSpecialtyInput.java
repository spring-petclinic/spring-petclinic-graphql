package org.springframework.samples.petclinic.graphql.types;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddSpecialtyInput {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
