package org.springframework.samples.petclinic.graphql;

import java.util.List;
import java.util.Map;

/**
 * @author Nils Hartmann
 */
public class AddVetInput {

    private String firstName;
    private String lastName;
    private List<Integer> specialtyIds;

    public static AddVetInput fromArgument(Map<String, Object> argument) {

        AddVetInput addVetInput = new AddVetInput();
        addVetInput.setFirstName((String) argument.get("firstName"));
        addVetInput.setLastName((String) argument.get("lastName"));
        addVetInput.setSpecialtyIds((List<Integer>) argument.get("specialtyIds"));

        return addVetInput;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Integer> getSpecialtyIds() {
        return specialtyIds;
    }

    public void setSpecialtyIds(List<Integer> specialtyIds) {
        this.specialtyIds = specialtyIds;
    }
}
