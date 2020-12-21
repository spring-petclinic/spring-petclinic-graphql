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
package org.springframework.samples.petclinic.vet.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.vet.*;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
@Component
public class SpecialtyMutationResolver implements GraphQLMutationResolver {
    private final static Logger logger = LoggerFactory.getLogger(SpecialtyMutationResolver.class);

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyMutationResolver(OwnerRepository ownerRepository, VisitRepository visitRepository, PetRepository petRepository, PetTypeRepository petTypeRepository, SpecialtyRepository specialtyRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.specialtyRepository = specialtyRepository;
    }





    public AddSpecialtyPayload addSpecialty(AddSpecialtyInput addSpecialtyInput) {
        Specialty specialty = new Specialty();
        specialty.setName(addSpecialtyInput.getName());
        specialtyRepository.save(specialty);
        return new AddSpecialtyPayload(specialty);
    }

    public UpdateSpecialtyPayload updateSpecialty(UpdateSpecialtyInput addSpecialtyInput) {
        Specialty specialty = specialtyRepository.findById(addSpecialtyInput.getSpecialtyId());
        specialty.setName(addSpecialtyInput.getName());
        specialtyRepository.save(specialty);
        return new UpdateSpecialtyPayload(specialty);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RemoveSpecialtyPayload removeSpecialty(RemoveSpecialtyInput removeSpecialtyInput) {
        Specialty specialty = specialtyRepository.findById(removeSpecialtyInput.getSpecialtyId());
        specialtyRepository.delete(specialty);

        return new RemoveSpecialtyPayload(List.copyOf(specialtyRepository.findAll()));
    }
}
