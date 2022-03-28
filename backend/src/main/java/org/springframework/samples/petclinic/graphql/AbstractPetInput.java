package org.springframework.samples.petclinic.graphql;

import java.time.LocalDate;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AbstractPetInput {

    private Integer typeId;
    private String name;
    private LocalDate birthDate;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AbstractPetInput{" +
            "typeId=" + typeId +
            ", name='" + name + '\'' +
            ", birthDate=" + birthDate +
            '}';
    }
}
