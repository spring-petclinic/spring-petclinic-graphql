package org.springframework.samples.petclinic.graphql.types;

import java.util.Date;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AbstractPetInput {

    private Integer typeId;
    private String name;
    private Date birthDate;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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
