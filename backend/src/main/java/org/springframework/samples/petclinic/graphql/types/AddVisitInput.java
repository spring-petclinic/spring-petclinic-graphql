package org.springframework.samples.petclinic.graphql.types;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddVisitInput {
    private int petId;
    private LocalDate date;
    private String description;

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
