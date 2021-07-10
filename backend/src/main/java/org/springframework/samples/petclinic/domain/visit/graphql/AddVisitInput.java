package org.springframework.samples.petclinic.domain.visit.graphql;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddVisitInput {
    private int petId;
    private Optional<Integer> vetId;
    private LocalDate date;
    private String description;

    public static AddVisitInput fromArgument(Map<String, Object> argument) {
        AddVisitInput addVisitInput = new AddVisitInput();
        addVisitInput.setPetId((int)argument.get("petId"));
        addVisitInput.setVetId(Optional.ofNullable((Integer)argument.get("vetId")));
        addVisitInput.setDate((LocalDate) argument.get("date"));
        addVisitInput.setDescription((String) argument.get("description"));

        return addVisitInput;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public Optional<Integer> getVetId() {
        return vetId;
    }

    public void setVetId(Optional<Integer> vetId) {
        this.vetId = vetId;
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
