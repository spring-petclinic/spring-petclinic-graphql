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
package org.springframework.samples.petclinic.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.graphql.types.*;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 * Multiple Mutations not (yet) supported. See: https://github.com/graphql-java/graphql-java-tools/pull/48
 */
@Component
public class Mutation implements GraphQLMutationResolver {
    private final static Logger logger = LoggerFactory.getLogger(Mutation.class);

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final SpecialtyRepository specialtyRepository;

    public Mutation(OwnerRepository ownerRepository, VisitRepository visitRepository, PetRepository petRepository, PetTypeRepository petTypeRepository, SpecialtyRepository specialtyRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.specialtyRepository = specialtyRepository;
    }

    public AddOwnerPayload addOwner(AddOwnerInput addOwnerInput) {
        final Owner owner = new Owner();
        owner.setAddress(addOwnerInput.getAddress());
        owner.setCity(addOwnerInput.getCity());
        owner.setTelephone(addOwnerInput.getTelephone());
        owner.setFirstName(addOwnerInput.getFirstName());
        owner.setLastName(addOwnerInput.getLastName());

        ownerRepository.save(owner);

        return new AddOwnerPayload(owner);
    }

    public UpdateOwnerPayload updateOwner(UpdateOwnerInput updateOwnerInput) {
        Owner owner = ownerRepository.findById(updateOwnerInput.getOwnerId());
        if (updateOwnerInput.getAddress() != null) {
            owner.setAddress(updateOwnerInput.getAddress());
        }

        if (updateOwnerInput.getFirstName() != null) {
            owner.setFirstName(updateOwnerInput.getFirstName());
        }

        if (updateOwnerInput.getLastName() != null) {
            owner.setLastName(updateOwnerInput.getLastName());
        }

        if (updateOwnerInput.getCity() != null) {
            owner.setCity(updateOwnerInput.getCity());
        }

        if (updateOwnerInput.getTelephone() != null) {
            owner.setTelephone(updateOwnerInput.getTelephone());
        }

        ownerRepository.save(owner);

        return new UpdateOwnerPayload(owner);
    }

    public AddPetPayload addPet(AddPetInput addPetInput) {

        final Owner owner = ownerRepository.findById(addPetInput.getOwnerId());
        final PetType type = petTypeRepository.findById(addPetInput.getTypeId());

        Pet pet = new Pet();
        pet.setName(addPetInput.getName());
        pet.setType(type);
        pet.setBirthDate(addPetInput.getBirthDate());
        pet.setOwner(owner);
        petRepository.save(pet);

        return new AddPetPayload(pet);
    }

    public UpdatePetPayload updatePet(UpdatePetInput updatePetInput) {
        final Pet pet = petRepository.findById(updatePetInput.getPetId());
        if (updatePetInput.getBirthDate() != null) {
            pet.setBirthDate(updatePetInput.getBirthDate());
        }

        if (updatePetInput.getName() != null) {
            pet.setName(updatePetInput.getName());
        }

        if (updatePetInput.getTypeId() != null) {
            PetType type = petTypeRepository.findById(updatePetInput.getTypeId());
            pet.setType(type);
        }

        petRepository.save(pet);

        return new UpdatePetPayload(pet);
    }

    public AddVisitPayload addVisit(AddVisitInput addVisitInput) {
        Pet pet = petRepository.findById(addVisitInput.getPetId());

        Visit visit = new Visit();
        visit.setDescription(addVisitInput.getDescription());
        visit.setPet(pet);
        visit.setDate(addVisitInput.getDate());

        visitRepository.save(visit);

        return new AddVisitPayload(visit);
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

        return new RemoveSpecialtyPayload(Lists.newArrayList(specialtyRepository.findAll()));
    }
}
