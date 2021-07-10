/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.domain.vet.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.domain.vet.Specialty;
import org.springframework.samples.petclinic.domain.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.domain.vet.SpecialtyService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class SpecialtyMutationWiring implements RuntimeWiringBuilderCustomizer {
    private final static Logger logger = LoggerFactory.getLogger(SpecialtyMutationWiring.class);

    private final SpecialtyService specialtyService;
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyMutationWiring(SpecialtyService specialtyService, SpecialtyRepository specialtyRepository) {
        this.specialtyService = specialtyService;
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder.type("Mutation", wiring -> wiring
            .dataFetcher("addSpecialty", this::addSpecialty)
            .dataFetcher("updateSpecialty", this::updateSpecialty)
            .dataFetcher("removeSpecialty", this::removeSpecialty)
        );
    }

    private AddSpecialtyPayload addSpecialty(DataFetchingEnvironment env) {
        Map<String, String> input = env.getArgument("input");

        Specialty specialty = specialtyService.addSpecialty(input.get("name"));

        return new AddSpecialtyPayload(specialty);
    }

    private UpdateSpecialtyPayload updateSpecialty(DataFetchingEnvironment env) {
        Map<String, ?> input = env.getArgument("input");

        Specialty specialty = specialtyService.updateSpecialty(
            (int) input.get("specialtyId"),
            (String) input.get("name"));

        return new UpdateSpecialtyPayload(specialty);
    }

    private RemoveSpecialtyPayload removeSpecialty(DataFetchingEnvironment env) {
        Map<String, Integer> input = env.getArgument("input");

        specialtyService.deleteSpecialty(input.get("specialtyId"));

        return new RemoveSpecialtyPayload(List.copyOf(specialtyRepository.findAll()));
    }
}
