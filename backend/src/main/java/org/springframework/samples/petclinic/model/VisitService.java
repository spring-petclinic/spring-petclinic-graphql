package org.springframework.samples.petclinic.model;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Validated
public class VisitService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PetRepository petRepository;
    private final VisitRepository visitRepository;

    public VisitService(ApplicationEventPublisher applicationEventPublisher, PetRepository petRepository, VisitRepository visitRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.petRepository = petRepository;
        this.visitRepository = visitRepository;
    }

    @Transactional
    public Visit addVisit(int petId, @NotEmpty String description, @NotNull LocalDate date, Optional<Integer> vetId) {
        Pet pet = petRepository.findById(petId);

        Visit visit = new Visit();
        visit.setDescription(description);
        visit.setPet(pet);
        visit.setDate(date);
        vetId.ifPresent(visit::setVetId);

        visitRepository.save(visit);
        applicationEventPublisher.publishEvent(new VisitCreatedEvent(visit.getId()));

        return visit;
    }
}
