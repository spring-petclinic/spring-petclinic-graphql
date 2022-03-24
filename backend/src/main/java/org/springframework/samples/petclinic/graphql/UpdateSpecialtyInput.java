package org.springframework.samples.petclinic.graphql;

import javax.validation.constraints.NotNull;

public class UpdateSpecialtyInput {
    @NotNull
    private Integer specialtyId;
    @NotNull
    private String name;

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public String getName() {
        return name;
    }
}
