package org.springframework.samples.petclinic.graphql.types;

import java.util.Date;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddVisitInput {
    private int petId;
    private Date date;
    private String description;

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
