package org.springframework.samples.petclinic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.samples.petclinic.auth.User;
import org.springframework.samples.petclinic.auth.UserRepository;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Imports testdata into the database on application startup
 *
 * @author Nils Hartmann
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private UserRepository userRepository;
    private VetRepository vetRepository;
    private SpecialtyRepository specialtyRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;
    private PetTypeRepository petTypeRepository;
    private PetRepository petRepository;

    public DatabaseInitializer(UserRepository userRepository, VetRepository vetRepository, SpecialtyRepository specialtyRepository, OwnerRepository ownerRepository, VisitRepository visitRepository, PetTypeRepository petTypeRepository, PetRepository petRepository) {
        this.userRepository = userRepository;
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.petTypeRepository = petTypeRepository;
        this.petRepository = petRepository;
    }

    private CycleIterator<Vet> vets;
    private CycleIterator<PetType> petTypes;
    private CycleIterator<Owner> owners;
    private CycleIterator<Pet> pets;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void run(String... args) throws Exception {
        newUser("susi", "Susi Smith", "ROLE_MANAGER");
        newUser("joe", "Joe Hill", "ROLE_USER");

        var radiology = newSpecialty(1, "radiology");
        var surgery = newSpecialty(2, "surgery");
        var dentistry = newSpecialty(3, "dentistry");

        vets = new CycleIterator<Vet>(List.of(
            newVet(1, "James", "Carter"),
            newVet(2, "Helen", "Leary", radiology),
            newVet(3, "Linda", "Douglas", surgery, dentistry),
            newVet(4, "Rafael", "Ortega", surgery),
            newVet(5, "Henry", "Stevens", radiology),
            newVet(6, "Sharon", "Jenkins")
        ));

        petTypes = new CycleIterator<PetType>(List.of(
            newPetType(1, "Cat"),
            newPetType(2, "Dog"),
            newPetType(3, "Lizard"),
            newPetType(4, "Snake"),
            newPetType(5, "Bird"),
            newPetType(6, "Hamster")
        ));

        owners = new CycleIterator<Owner>(importOwners());

        pets = new CycleIterator<Pet>(importPets());

        importVisits();
    }

    private List<Pet> importPets() throws Exception {
        return readCsv("pets.csv", (index, parts) -> {
            Pet pet = new Pet();
            pet.setBirthDate(asLocalDate(parts[0]));
            pet.setName(parts[1]);
            pet.setType(petTypes.next());

            final Owner owner = owners.next();
            owner.addPet(pet);
            petRepository.save(pet);
            ownerRepository.save(owner);
            return pet;
        });
    }

    private void importVisits() throws Exception {
        pets.reset();
        vets.reset();
        AtomicInteger ix = new AtomicInteger();
        readCsv("visits.csv", (index, parts) -> {
            Visit visit = new Visit();
            visit.setDate(asLocalDate(parts[0]));
            visit.setDescription(String.format("%s %s", parts[1], parts[2]));
            visit.setPetId(pets.next().getId());
            if (ix.incrementAndGet() % 2 == 0) {
                visit.setVetId(vets.next().getId());
            }
            visitRepository.save(visit);
            return visit;
        });
    }

    private List<Owner> importOwners() throws Exception {
        return readCsv("owners.csv", (index, parts) -> {
            Owner owner = new Owner();
            owner.setFirstName(parts[0]);
            owner.setLastName(parts[1]);
            owner.setAddress(parts[2]);
            owner.setCity(parts[3]);
            owner.setTelephone(parts[4]);
            ownerRepository.save(owner);
            return owner;
        });
    }

    private PetType newPetType(int id, String name) {
        PetType petType = new PetType();
        petType.setName(name);
        petTypeRepository.save(petType);
        return petType;
    }

    private Vet newVet(int id, String firstName, String lastName, Specialty... specialties) {
        Vet vet = new Vet();
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        for (Specialty specialty : specialties) {
            vet.addSpecialty(specialty);
        }

        vetRepository.save(vet);
        return vet;
    }

    private Specialty newSpecialty(int id, String name) {
        Specialty specialty = new Specialty();
        specialty.setName(name);
        specialtyRepository.save(specialty);
        return specialty;
    }

    private void newUser(String name, String fullname, String role) {
        User user = new User();
        user.setUsername(name);
        user.setPassword("{noop}" + name);
        user.setFullname(fullname);
        user.setEnabled(true);
        user.addRole(role);
        userRepository.save(user);

    }

    private static LocalDate asLocalDate(String date) throws Exception {
        var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, format);
    }

    static class CycleIterator<E> {
        private int index = -1;
        private final List<E> elements;

        public CycleIterator(List<E> elements) {
            this.elements = elements;
        }

        void reset() {
            index = -1;
        }

        public E next() {
            index++;
            if (index >= elements.size()) {
                index = 0;
            }
            return elements.get(index);
        }
    }

    private <E> List<E> readCsv(String name, CsvLineConsumer<E> consumer) throws Exception {
        final List<E> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/testdata/" + name)))) {
            String line = null;
            int index = 0;
            while ((line = br.readLine()) != null) {
                if (index == 0) {
                    // ignore first line (header)
                    index++;
                    continue;
                }
                String[] parts = line.trim().split("\\,");
                E e = consumer.consume(index, parts);
                result.add(e);
                index++;
            }
        }
        return result;
    }

    @FunctionalInterface
    interface CsvLineConsumer<E> {
        E consume(int lineNo, String[] parts) throws Exception;
    }
}
