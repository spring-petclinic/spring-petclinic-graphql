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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

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
abstract class AbstractClinicRepositoryTests {

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
    void shouldFindOwnersByLastName() {
        Collection<Owner> owners = this.ownerRepository.findByLastName("Davis");
        assertThat(owners.size()).isEqualTo(2);

        owners = this.ownerRepository.findByLastName("Daviss");
        assertThat(owners.isEmpty()).isTrue();
    }

    @Test
    void shouldFindSingleOwnerWithPet() {
        Owner owner = this.ownerRepository.findById(1);
        assertThat(owner.getLastName()).startsWith("Franklin");
        assertThat(owner.getPets().size()).isEqualTo(1);
        assertThat(owner.getPets().get(0).getType()).isNotNull();
        assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("cat");
    }

    @Test
    @Transactional
    void shouldInsertOwner() {
        Collection<Owner> owners = this.ownerRepository.findByLastName("Schultz");
        int found = owners.size();

        Owner owner = new Owner();
        owner.setFirstName("Sam");
        owner.setLastName("Schultz");
        owner.setAddress("4, Evans Street");
        owner.setCity("Wollongong");
        owner.setTelephone("4444444444");
        this.ownerRepository.save(owner);
        assertThat(owner.getId().longValue()).isNotEqualTo(0);

        owners = this.ownerRepository.findByLastName("Schultz");
        assertThat(owners.size()).isEqualTo(found + 1);
    }

    @Test
    @Transactional
    void shouldUpdateOwner() {
        Owner owner = this.ownerRepository.findById(1);
        String oldLastName = owner.getLastName();
        String newLastName = oldLastName + "X";

        owner.setLastName(newLastName);
        this.ownerRepository.save(owner);

        // retrieving new name from database
        owner = this.ownerRepository.findById(1);
        assertThat(owner.getLastName()).isEqualTo(newLastName);
    }

    @Test
    void shouldFindPetWithCorrectId() {
        Pet pet7 = this.petRepository.findById(7);
        assertThat(pet7.getName()).startsWith("Samantha");
        assertThat(pet7.getOwner().getFirstName()).isEqualTo("Jean");

    }

//    @Test
//    public void shouldFindAllPetTypes() {
//        Collection<PetType> petTypes = this.clinicService.findPetTypes();
//
//        PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
//        assertThat(petType1.getName()).isEqualTo("cat");
//        PetType petType4 = EntityUtils.getById(petTypes, PetType.class, 4);
//        assertThat(petType4.getName()).isEqualTo("snake");
//    }

    @Test
    @Transactional
    void shouldInsertPetIntoDatabaseAndGenerateId() {
        Owner owner6 = this.ownerRepository.findById(6);
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

        owner6 = this.ownerRepository.findById(6);
        assertThat(owner6.getPets().size()).isEqualTo(found + 1);
        // checks that id has been generated
        assertThat(pet.getId()).isNotNull();
    }

    @Test
    @Transactional
    void shouldUpdatePetName() {
        Pet pet7 = this.petRepository.findById(7);
        String oldName = pet7.getName();

        String newName = oldName + "X";
        pet7.setName(newName);
        this.petRepository.save(pet7);

        pet7 = this.petRepository.findById(7);
        assertThat(pet7.getName()).isEqualTo(newName);
    }

    @Test
    void shouldFindVets() {
        Collection<Vet> vets = this.vetRepository.findAll();

        Vet vet = EntityUtils.getById(vets, Vet.class, 3);
        assertThat(vet.getLastName()).isEqualTo("Douglas");
        assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
        assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
        assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("surgery");
    }

    @Test
    @Transactional
    void shouldAddNewVisitForPet() {
        Pet pet7 = this.petRepository.findById(7);
        int found = pet7.getVisits().size();
        Visit visit = new Visit();
        pet7.addVisit(visit);
        visit.setDescription("test");
        this.visitRepository.save(visit);
        this.petRepository.save(pet7);

        pet7 = this.petRepository.findById(7);
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
    void shouldFindAllPets(){
        Collection<Pet> pets = this.petRepository.findAll();
        Pet pet1 = EntityUtils.getById(pets, Pet.class, 1);
        assertThat(pet1.getName()).isEqualTo("Leo");
        Pet pet3 = EntityUtils.getById(pets, Pet.class, 3);
        assertThat(pet3.getName()).isEqualTo("Rosy");
    }

    @Test
    @Transactional
    void shouldDeletePet(){
        Pet pet = this.petRepository.findById(1);
        this.petRepository.delete(pet);
        flush();
        try {
        pet = this.petRepository.findById(1);
		} catch (Exception e) {
			pet = null;
		}
        assertThat(pet).isNull();
    }

    @Test
    void shouldFindVisitDyId(){
    	Visit visit = this.visitRepository.findById(1);
    	assertThat(visit.getId()).isEqualTo(1);
    	assertThat(visit.getPet().getName()).isEqualTo("Samantha");
    }

    @Test
    void shouldFindAllVisits(){
        Collection<Visit> visits = this.visitRepository.findAll();
        Visit visit1 = EntityUtils.getById(visits, Visit.class, 1);
        assertThat(visit1.getPet().getName()).isEqualTo("Samantha");
        Visit visit3 = EntityUtils.getById(visits, Visit.class, 3);
        assertThat(visit3.getPet().getName()).isEqualTo("Max");
    }

    @Test
    @Transactional
    void shouldInsertVisit() {
        Collection<Visit> visits = this.visitRepository.findAll();
        int found = visits.size();

        Pet pet = this.petRepository.findById(1);

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
    @Transactional
    void shouldUpdateVisit(){
    	Visit visit = this.visitRepository.findById(1);
    	String oldDesc = visit.getDescription();
        String newDesc = oldDesc + "X";
        visit.setDescription(newDesc);
        this.visitRepository.save(visit);
        visit = this.visitRepository.findById(1);
        assertThat(visit.getDescription()).isEqualTo(newDesc);
    }

    @Test
    @Transactional
    void shouldDeleteVisit(){
    	Visit visit = this.visitRepository.findById(1);
        this.visitRepository.delete(visit);
        flush();
        try {
        	visit = this.visitRepository.findById(1);
		} catch (Exception e) {
			visit = null;
		}
        assertThat(visit).isNull();
    }

    @Test
    void shouldFindVetDyId(){
    	Vet vet = this.vetRepository.findById(1);
    	assertThat(vet.getFirstName()).isEqualTo("James");
    	assertThat(vet.getLastName()).isEqualTo("Carter");
    }

    @Test
    @Transactional
    void shouldInsertVet() {
        Collection<Vet> vets = this.vetRepository.findAll();
        int found = vets.size();

        Vet vet = new Vet();
        vet.setFirstName("John");
        vet.setLastName("Dow");

        this.vetRepository.save(vet);
        assertThat(vet.getId().longValue()).isNotEqualTo(0);

        vets = this.vetRepository.findAll();
        assertThat(vets.size()).isEqualTo(found + 1);
    }

    @Test
    @Transactional
    void shouldUpdateVet(){
    	Vet vet = this.vetRepository.findById(1);
    	String oldLastName = vet.getLastName();
        String newLastName = oldLastName + "X";
        vet.setLastName(newLastName);
        this.vetRepository.save(vet);
        vet = this.vetRepository.findById(1);
        assertThat(vet.getLastName()).isEqualTo(newLastName);
    }

    @Test
    @Transactional
    void shouldDeleteVet(){
    	Vet vet = this.vetRepository.findById(1);
        this.vetRepository.delete(vet);
        try {
        	vet = this.vetRepository.findById(1);
		} catch (Exception e) {
			vet = null;
		}
        assertThat(vet).isNull();
    }

    @Test
    void shouldFindAllOwners(){
        Collection<Owner> owners = this.ownerRepository.findAll();
        Owner owner1 = EntityUtils.getById(owners, Owner.class, 1);
        assertThat(owner1.getFirstName()).isEqualTo("George");
        Owner owner3 = EntityUtils.getById(owners, Owner.class, 3);
        assertThat(owner3.getFirstName()).isEqualTo("Eduardo");
    }

    @Test
    @Transactional
    void shouldDeleteOwner(){
    	Owner owner = this.ownerRepository.findById(1);
        this.ownerRepository.delete(owner);
        try {
        	owner = this.ownerRepository.findById(1);
		} catch (Exception e) {
			owner = null;
		}
        assertThat(owner).isNull();
    }

    @Test
    void shouldFindPetTypeById(){
    	PetType petType = findPetTypeById(1);
    	assertThat(petType.getName()).isEqualTo("cat");
    }

    private PetType findPetTypeById(int petTypeId) {
        PetType petType = null;
        try {
            petType = petTypeRepository.findById(petTypeId);
        } catch (ObjectRetrievalFailureException |EmptyResultDataAccessException e) {
            // just ignore not found exceptions for Jdbc/Jpa realization
            return null;
        }
        return petType;
    }

    @Test
    public void shouldFindAllPetTypes(){
        Collection<PetType> petTypes = this.petRepository.findPetTypes();
        PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
        assertThat(petType1.getName()).isEqualTo("cat");
        PetType petType3 = EntityUtils.getById(petTypes, PetType.class, 3);
        assertThat(petType3.getName()).isEqualTo("lizard");
    }

    @Test
    @Transactional
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
    @Transactional
    public void shouldUpdatePetType(){
    	PetType petType = this.petTypeRepository.findById(1);
    	String oldLastName = petType.getName();
        String newLastName = oldLastName + "X";
        petType.setName(newLastName);
        this.petTypeRepository.save(petType);
        petType = this.petTypeRepository.findById(1);
        assertThat(petType.getName()).isEqualTo(newLastName);
    }

    @Test
    @Transactional
    public void shouldDeletePetType(){
    	PetType petType = this.petTypeRepository.findById(1);
        this.petTypeRepository.delete(petType);
        flush();
        try {
        	petType = this.petTypeRepository.findById(1);
		} catch (Exception e) {
			petType = null;
		}
        assertThat(petType).isNull();
    }

    @Test
    public void shouldFindSpecialtyById(){
    	Specialty specialty = this.specialtyRepository.findById(1);
    	assertThat(specialty.getName()).isEqualTo("radiology");
    }

    @Test
    public void shouldFindAllSpecialtys(){
        Collection<Specialty> specialties = this.specialtyRepository.findAll();
        Specialty specialty1 = EntityUtils.getById(specialties, Specialty.class, 1);
        assertThat(specialty1.getName()).isEqualTo("radiology");
        Specialty specialty3 = EntityUtils.getById(specialties, Specialty.class, 3);
        assertThat(specialty3.getName()).isEqualTo("dentistry");
    }

    @Test
    @Transactional
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
    @Transactional
    public void shouldUpdateSpecialty(){
    	Specialty specialty = this.specialtyRepository.findById(1);
    	String oldLastName = specialty.getName();
        String newLastName = oldLastName + "X";
        specialty.setName(newLastName);
        this.specialtyRepository.save(specialty);
        specialty = this.specialtyRepository.findById(1);
        assertThat(specialty.getName()).isEqualTo(newLastName);
    }

    @Test
    @Transactional
    public void shouldDeleteSpecialty(){
    	Specialty specialty = this.specialtyRepository.findById(1);
        this.specialtyRepository.delete(specialty);
        flush();
        try {
        	specialty = this.specialtyRepository.findById(1);
		} catch (Exception e) {
			specialty = null;
		}
        assertThat(specialty).isNull();
    }

    protected void flush() {
        // Nothing by default
    }

}
