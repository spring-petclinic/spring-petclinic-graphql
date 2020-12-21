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

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.samples.petclinic.graphql.types.OwnerFilter;
import org.springframework.samples.petclinic.graphql.types.OwnerOrder;
import org.springframework.samples.petclinic.graphql.types.VisitConnection;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for PetClinic Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
@SuppressWarnings("unused")
public class Query implements GraphQLQueryResolver {

    private final PetTypeRepository petTypeRepository;
    private final VetRepository vetRepository;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final SpecialtyRepository specialtyRepository;

    public Query(PetTypeRepository petTypeRepository, VetRepository vetRepository, OwnerRepository ownerRepository, PetRepository petRepository, SpecialtyRepository specialtyRepository) {
        this.petTypeRepository = petTypeRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.specialtyRepository = specialtyRepository;
    }

    public List<PetType> pettypes() {
        return List.copyOf(petTypeRepository.findAll());
    }

    public List<Vet> vets() {
        return List.copyOf(vetRepository.findAll());
    }

    public List<Owner> owners(OwnerFilter filter, List<OwnerOrder> orders) {
        return List.copyOf(ownerRepository.findAllByFilterOrder(filter, orders));
    }

    public Owner owner(int id) {
        return ownerRepository.findById(id);
    }

    public Pet pet(int id) {
        return petRepository.findById(id);
    }

    public List<Pet> pets() {
        return List.copyOf(petRepository.findAll());
    }

    public List<Specialty> specialties() {
        return List.copyOf(specialtyRepository.findAll());
    }

    public VisitConnection getVisitConnection() {
        return new VisitConnection(null);
    }

}
