package org.springframework.samples.petclinic.graphql;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.SpecialtyService;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * GraphQL handler functions for "Specialty" GraphQL type, Query and Mutation
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class SpecialtyController {

    private static final Logger log = LoggerFactory.getLogger(SpecialtyController.class);

    private final SpecialtyService specialtyService;
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyController(SpecialtyService specialtyService, SpecialtyRepository specialtyRepository) {
        this.specialtyService = specialtyService;
        this.specialtyRepository = specialtyRepository;
    }

    @QueryMapping
    public List<Specialty> specialties() {
      return  List.copyOf(specialtyRepository.findAll());
    }

    /**
     * EXAMPLE:
     * --------------------------
     *
     * - Annotated Controllers have access to DataFetchingEnvironment
     *  - Receive arguments using DataFetchingEnvironment
     */
    @MutationMapping
    public AddSpecialtyPayload addSpecialty(DataFetchingEnvironment env) {
        Map<String, String> input = env.getArgument("input");

        Specialty specialty = specialtyService.addSpecialty(input.get("name"));

        return new AddSpecialtyPayload(specialty);
    }

    @MutationMapping
    public UpdateSpecialtyPayload updateSpecialty(@Argument UpdateSpecialtyInput input) {
        Specialty specialty = specialtyService.updateSpecialty(
            input.getSpecialtyId(),
            input.getName()
        );

        return new UpdateSpecialtyPayload(specialty);
    }

    @MutationMapping
    public RemoveSpecialtyPayload removeSpecialty(DataFetchingEnvironment env) {
        Map<String, Integer> input = env.getArgument("input");

        specialtyService.deleteSpecialty(input.get("specialtyId"));

        return new RemoveSpecialtyPayload(List.copyOf(specialtyRepository.findAll()));
    }
}
