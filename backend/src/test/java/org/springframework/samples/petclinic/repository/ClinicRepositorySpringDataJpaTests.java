/*
 * Copyright 2002-2017 the original author or authors.
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
package org.springframework.samples.petclinic.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.samples.petclinic.PetClinicTestDbConfiguration;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.samples.petclinic.model.OwnerFilter.NO_FILTER;

/**
 * <p> Base class for Repository integration tests. </p> <p> Subclasses should specify Spring context
 * configuration using {@link ContextConfiguration @ContextConfiguration} annotation </p> <p>
 * AbstractclinicServiceTests and its subclasses benefit from the following services provided by the Spring
 * TestContext Framework: </p> <ul> <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li> <li><strong>Dependency Injection</strong> of test fixture instances, meaning that
 * we don't need to perform application context lookups.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in its own transaction,
 * which is automatically rolled back by default. Thus, even if tests insert or otherwise change database state, there
 * is no need for a teardown or cleanup script. <li> An {@link org.springframework.context.ApplicationContext
 * ApplicationContext} is also inherited and can be used for explicit bean lookup if necessary. </li> </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
@SpringBootTest
@Transactional
@Import(PetClinicTestDbConfiguration.class)
class ClinicRepositorySpringDataJpaTests {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindFall() {
        OwnerFilter filter = new OwnerFilter();
        filter.setLastName("Escobito");
        var page = ownerRepository.findAll(filter, PageRequest.ofSize(10));
        assertThat(page.getTotalElements()).isEqualTo(1L);
        assertThat(page.getContent().get(0).getLastName()).isEqualTo("Escobito");

        filter = new OwnerFilter();
        // lastname search is "Starts with", i.e. all names that start
        // with "es" should be returned (case insensitive)
        filter.setLastName("es");
        page = ownerRepository.findAll(filter, PageRequest.ofSize(10));
        assertThat(page.getTotalElements()).isEqualTo(2L);
    }

    @Test
    void shouldFindSingleOwnerWithPet() {
        Owner owner = this.ownerRepository.findById(1).orElseThrow();
        assertThat(owner.getLastName()).startsWith("Franklin");
        assertThat(owner.getPets().size()).isEqualTo(1);
        assertThat(owner.getPets().get(0).getType()).isNotNull();
        assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("Cat");
    }

    @Test
    void shouldInsertOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Sam");
        owner.setLastName("Schultz");
        owner.setAddress("4, Evans Street");
        owner.setCity("Wollongong");
        owner.setTelephone("4444444444");
        this.ownerRepository.save(owner);
        assertThat(owner.getId().longValue()).isNotEqualTo(0);
    }

    @Test
    void shouldUpdateOwner() {
        Owner owner = this.ownerRepository.findById(1).orElseThrow();
        String oldLastName = owner.getLastName();
        String newLastName = oldLastName + "X";

        owner.setLastName(newLastName);
        this.ownerRepository.save(owner);

        // retrieving new name from database
        owner = this.ownerRepository.findById(1).orElseThrow();
        assertThat(owner.getLastName()).isEqualTo(newLastName);
    }

    @Test
    void shouldFindPetWithCorrectId() {
        Pet pet7 = this.petRepository.findById(7).orElseThrow();
        assertThat(pet7.getName()).startsWith("Samantha");
        assertThat(pet7.getOwner().getFirstName()).isEqualTo("Jean");
    }

    @Test
    void shouldReturnEmptyOnMissingPet() {
        var unknownPet = this.petRepository.findById(7777);
        assertFalse(unknownPet.isPresent());
    }

    @Test
    void shouldInsertPetIntoDatabaseAndGenerateId() {
        Owner owner6 = this.ownerRepository.findById(6).orElseThrow();
        int found = owner6.getPets().size();

        Pet pet = new Pet();
        pet.setName("bowser");
        Collection<PetType> types = this.petRepository.findPetTypes();
        pet.setType(EntityUtils.getById(types, PetType.class, 2));
        pet.setBirthDate(EntityUtils.asDateTime(new Date()));
        owner6.addPet(pet);
        assertThat(owner6.getPets().size()).isEqualTo(found + 1);

        this.petRepository.save(pet);
        this.ownerRepository.save(owner6);

        owner6 = this.ownerRepository.findById(6).orElseThrow();
        assertThat(owner6.getPets().size()).isEqualTo(found + 1);
        // checks that id has been generated
        assertThat(pet.getId()).isNotNull();
    }

    @Test
    void shouldUpdatePetName() {
        Pet pet7 = this.petRepository.findById(7).orElseThrow();
        String oldName = pet7.getName();

        String newName = oldName + "X";
        pet7.setName(newName);
        this.petRepository.save(pet7);

        pet7 = this.petRepository.findById(7).orElseThrow();
        assertThat(pet7.getName()).isEqualTo(newName);
    }

    @Test
    void shouldFindVets() {
        var vets = this.vetRepository.findBy(ScrollPosition.offset(), Sort.by("lastName", "firstName"), Limit.of(2));

        assertThat(vets.size()).isEqualTo(2);
        assertThat(vets.getContent().get(0).getId()).isEqualTo(1);
        assertThat(vets.getContent().get(1).getId()).isEqualTo(3);
    }

    @Test
    void shouldAddNewVisitForPet() {
        Pet pet7 = this.petRepository.findById(7).orElseThrow();
        int found = pet7.getVisits().size();
        Visit visit = new Visit();
        pet7.addVisit(visit);
        visit.setDescription("test");
        this.visitRepository.save(visit);
        this.petRepository.save(pet7);

        pet7 = this.petRepository.findById(7).orElseThrow();
        assertThat(pet7.getVisits().size()).isEqualTo(found + 1);
        assertThat(visit.getId()).isNotNull();
    }

    @Test
    void shouldFindVisitsByPetId() throws Exception {
        Collection<Visit> visits = this.visitRepository.findByPetIdOrderById(7);
        assertThat(visits.size()).isEqualTo(2);
        Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
        assertThat(visitArr[0].getPet()).isNotNull();
        assertThat(visitArr[0].getDate()).isNotNull();
        assertThat(visitArr[0].getPet().getId()).isEqualTo(7);
    }

    @Test
    void shouldFindAllPets() {
        Collection<Pet> pets = this.petRepository.findAll();
        Pet pet1 = EntityUtils.getById(pets, Pet.class, 1);
        assertThat(pet1.getName()).isEqualTo("Leo");
        Pet pet3 = EntityUtils.getById(pets, Pet.class, 3);
        assertThat(pet3.getName()).isEqualTo("Rosy");
    }

    @Test
    void shouldDeletePet() {
        Pet pet = this.petRepository.findById(1).orElseThrow();
        this.petRepository.delete(pet);
        try {
            pet = this.petRepository.findById(1).orElseThrow();
        } catch (Exception e) {
            pet = null;
        }
        assertThat(pet).isNull();
    }

    @Test
    void shouldFindVisitDyId() {
        Visit visit = this.visitRepository.findById(1).orElseThrow();
        assertThat(visit.getId()).isEqualTo(1);
        assertThat(visit.getPet().getName()).isEqualTo("Samantha");
    }

    @Test
    void shouldFindAllVisits() {
        Collection<Visit> visits = this.visitRepository.findAll();
        Visit visit1 = EntityUtils.getById(visits, Visit.class, 1);
        assertThat(visit1.getPet().getName()).isEqualTo("Samantha");
        Visit visit3 = EntityUtils.getById(visits, Visit.class, 3);
        assertThat(visit3.getPet().getName()).isEqualTo("Max");
    }

    @Test
    void shouldInsertVisit() {
        Collection<Visit> visits = this.visitRepository.findAll();
        int found = visits.size();

        Pet pet = this.petRepository.findById(1).orElseThrow();

        Visit visit = new Visit();
        visit.setPet(pet);
        visit.setDate(EntityUtils.asDateTime(new Date()));
        visit.setDescription("new visit");


        this.visitRepository.save(visit);
        assertThat(visit.getId().longValue()).isNotEqualTo(0);

        visits = this.visitRepository.findAll();
        assertThat(visits.size()).isEqualTo(found + 1);
    }

    @Test
    void shouldUpdateVisit() {
        Visit visit = this.visitRepository.findById(1).orElseThrow();
        String oldDesc = visit.getDescription();
        String newDesc = oldDesc + "X";
        visit.setDescription(newDesc);
        this.visitRepository.save(visit);
        visit = this.visitRepository.findById(1).orElseThrow();
        assertThat(visit.getDescription()).isEqualTo(newDesc);
    }

    @Test
    void shouldDeleteVisit() {
        Visit visit = this.visitRepository.findById(1).orElseThrow();
        this.visitRepository.delete(visit);
        visit = this.visitRepository.findById(1).orElse(null);
        assertThat(visit).isNull();
    }

    @Test
    void shouldFindVetDyId() {
        Vet vet = this.vetRepository.findById(1).orElseThrow();
        assertThat(vet.getFirstName()).isEqualTo("James");
        assertThat(vet.getLastName()).isEqualTo("Carter");
    }

    @Test
    void shouldInsertVet() {
        Vet vet = new Vet();
        vet.setFirstName("John");
        vet.setLastName("Dow");

        var newVet = this.vetRepository.save(vet);
        assertThat(newVet.getId()).isNotNull();

        var foundVet = this.vetRepository.findById(newVet.getId());
        assertThat(foundVet.isPresent()).isTrue();
    }

    @Test
    void shouldUpdateVet() {
        Vet vet = this.vetRepository.findById(1).orElseThrow();
        String oldLastName = vet.getLastName();
        String newLastName = oldLastName + "X";
        vet.setLastName(newLastName);
        this.vetRepository.save(vet);
        vet = this.vetRepository.findById(1).orElseThrow();
        assertThat(vet.getLastName()).isEqualTo(newLastName);
    }

    @Test
    void shouldDeleteVet() {
        Vet vet = this.vetRepository.findById(1).orElseThrow();
        this.vetRepository.delete(vet);
        vet = this.vetRepository.findById(1).orElse(null);
        assertThat(vet).isNull();
    }

    @Test
    void shouldFindAllOwners() {
        Collection<Owner> owners = this.ownerRepository.findAll();
        Owner owner1 = EntityUtils.getById(owners, Owner.class, 1);
        assertThat(owner1.getFirstName()).isEqualTo("George");
        Owner owner3 = EntityUtils.getById(owners, Owner.class, 3);
        assertThat(owner3.getFirstName()).isEqualTo("Eduardo");
    }

    @Test
    void shouldFindOwnersPaginated() {
        var owners = this.ownerRepository.findBy(NO_FILTER, c -> c.
            limit(5)
            .sortBy(Sort.by("lastName"))
            .scroll(ScrollPosition.offset()));

        assertThat(owners.size()).isEqualTo(5);
        assertThat(owners.getContent().get(0).getId()).isEqualTo(48);
        assertThat(owners.getContent().get(1).getId()).isEqualTo(7);
        assertThat(owners.getContent().get(2).getId()).isEqualTo(55);
        assertThat(owners.getContent().get(3).getId()).isEqualTo(19);
        assertThat(owners.getContent().get(4).getId()).isEqualTo(6);

        var newPosition = owners.positionAt(2);
        owners = this.ownerRepository.findBy(NO_FILTER, c -> c.
            limit(5)
            .sortBy(Sort.by("lastName"))
            .scroll(newPosition));
        assertThat(owners.size()).isEqualTo(5);
        assertThat(owners.getContent().get(0).getId()).isEqualTo(19);
        assertThat(owners.getContent().get(1).getId()).isEqualTo(6);
        assertThat(owners.getContent().get(2).getId()).isEqualTo(17);
        assertThat(owners.getContent().get(3).getId()).isEqualTo(2);
        assertThat(owners.getContent().get(4).getId()).isEqualTo(4);

        // with filter
        var filter = new OwnerFilter();
        filter.setLastName("da");
        owners = this.ownerRepository.findBy(filter, c -> c.
            limit(5)
            .sortBy(Sort.by("lastName"))
            .scroll(ScrollPosition.offset(1)));
        assertThat(owners.size()).isEqualTo(2);
        assertThat(owners.getContent().get(0).getId()).isEqualTo(2);
        assertThat(owners.getContent().get(1).getId()).isEqualTo(4);

    }

    @Test
    void shouldDeleteOwner() {
        Owner owner = this.ownerRepository.findById(1).orElseThrow();
        this.ownerRepository.delete(owner);
        try {
            owner = this.ownerRepository.findById(1).orElseThrow();
        } catch (Exception e) {
            owner = null;
        }
        assertThat(owner).isNull();
    }

    @Test
    void shouldFindPetTypeById() {
        PetType petType = findPetTypeById(1);
        assertThat(petType.getName()).isEqualTo("Cat");
    }

    private PetType findPetTypeById(int petTypeId) {
        return petTypeRepository.findById(petTypeId).orElse(null);
    }

    @Test
    public void shouldFindAllPetTypes() {
        Collection<PetType> petTypes = this.petRepository.findPetTypes();
        PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
        assertThat(petType1.getName()).isEqualTo("Cat");
        PetType petType3 = EntityUtils.getById(petTypes, PetType.class, 3);
        assertThat(petType3.getName()).isEqualTo("Lizard");
    }

    @Test
    public void shouldInsertPetType() {
        Collection<PetType> petTypes = this.petTypeRepository.findAll();
        int found = petTypes.size();

        PetType petType = new PetType();
        petType.setName("tiger");

        this.petTypeRepository.save(petType);
        assertThat(petType.getId().longValue()).isNotEqualTo(0);

        petTypes = this.petTypeRepository.findAll();
        assertThat(petTypes.size()).isEqualTo(found + 1);
    }

    @Test
    public void shouldUpdatePetType() {
        PetType petType = this.petTypeRepository.findById(1).orElseThrow();
        String oldLastName = petType.getName();
        String newLastName = oldLastName + "X";
        petType.setName(newLastName);
        this.petTypeRepository.save(petType);
        petType = this.petTypeRepository.findById(1).orElseThrow();
        assertThat(petType.getName()).isEqualTo(newLastName);
    }

    @Test
    public void shouldFindSpecialtyById() {
        Specialty specialty = this.specialtyRepository.findById(1).orElseThrow();
        assertThat(specialty.getName()).isEqualTo("radiology");

    }

    @Test
    public void shouldFindAllSpecialtys() {
        Collection<Specialty> specialties = this.specialtyRepository.findAll();
        Specialty specialty1 = EntityUtils.getById(specialties, Specialty.class, 1);
        assertThat(specialty1.getName()).isEqualTo("radiology");
        Specialty specialty3 = EntityUtils.getById(specialties, Specialty.class, 3);
        assertThat(specialty3.getName()).isEqualTo("dentistry");
    }

    @Test
    public void shouldInsertSpecialty() {
        Collection<Specialty> specialties = this.specialtyRepository.findAll();
        int found = specialties.size();

        Specialty specialty = new Specialty();
        specialty.setName("dermatologist");

        this.specialtyRepository.save(specialty);
        assertThat(specialty.getId().longValue()).isNotEqualTo(0);

        specialties = this.specialtyRepository.findAll();
        assertThat(specialties.size()).isEqualTo(found + 1);
    }

    @Test
    public void shouldUpdateSpecialty() {
        Specialty specialty = this.specialtyRepository.findById(1).orElseThrow();
        String oldLastName = specialty.getName();

        String newLastName = oldLastName + "X";
        specialty.setName(newLastName);
        this.specialtyRepository.save(specialty);
        specialty = this.specialtyRepository.findById(1).orElseThrow();
        assertThat(specialty.getName()).isEqualTo(newLastName);

    }

    @Test
    public void shouldDeleteSpecialty() {
        Specialty specialty = this.specialtyRepository.findById(1).orElseThrow();
        this.specialtyRepository.delete(specialty);
        specialty = this.specialtyRepository.findById(1).orElse(null);
        assertThat(specialty).isNull();
    }
}
