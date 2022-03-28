package org.springframework.samples.petclinic.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VetService {
    private static final Logger log = LoggerFactory.getLogger(VetService.class);

    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;

    public VetService(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Vet createVet(String firstName, String lastName, List<Integer> specialtyIds) throws InvalidVetDataException {
        Vet vet = new Vet();
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        for (Integer specialtyId : specialtyIds) {
            log.info("Specialty Id '{}'", specialtyId);
            Specialty specialty = specialtyRepository.findById(specialtyId);
            log.info("Specialty '{}'", specialty);
            if (specialty == null) {
                throw new InvalidVetDataException("Specialty with Id '%s' not found", specialtyId);
            }
            vet.addSpecialty(specialty);
        }

        vetRepository.save(vet);

        log.info("VET {}", vet);

        return vet;
    }
}
