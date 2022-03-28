package org.springframework.samples.petclinic.model;

import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Service
@Validated
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;


    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Transactional
    public Specialty addSpecialty(@NotEmpty  String name) {
        Specialty specialty = new Specialty();
        specialty.setName(name);
        specialtyRepository.save(specialty);
        return specialty;
    }

    @Transactional
    public Specialty updateSpecialty(int specialtyId, @NotEmpty String newName) {
        Specialty specialty = specialtyRepository.findById(specialtyId);
        specialty.setName(newName);
        specialtyRepository.save(specialty);
        return specialty;
    }

    @Transactional
    public void deleteSpecialty(int specialtyId) {
        Specialty specialty = specialtyRepository.findById(specialtyId);
        specialtyRepository.delete(specialty);
    }
}
