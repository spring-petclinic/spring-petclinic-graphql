package org.springframework.samples.petclinic.domain.vet.graphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.samples.petclinic.domain.owner.PetRepository;
import org.springframework.samples.petclinic.domain.vet.*;
import org.springframework.samples.petclinic.domain.visit.Visit;
import org.springframework.samples.petclinic.domain.visit.VisitRepository;
import org.springframework.samples.petclinic.domain.visit.graphql.VisitConnection;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class VetController {

    private static final Logger log = LoggerFactory.getLogger(VetController.class);

    private final VetService vetService;
    private final VetRepository vetRepository;
    private final VisitRepository visitRepository;

    public VetController(VetService vetService, VetRepository vetRepository, VisitRepository visitRepository) {
        this.vetService = vetService;
        this.vetRepository = vetRepository;
        this.visitRepository = visitRepository;
    }

    @QueryMapping
    public List<Vet> vets() {
        return List.copyOf(vetRepository.findAll());
    }

    @QueryMapping
    public Optional<Vet> vet(@Argument Integer id) {
        return vetRepository.findById(id);
    }

    @SchemaMapping
    public VisitConnection visits(Vet vet) {
        List<Visit> visitList = visitRepository.findByVetId(vet.getId());
        return new VisitConnection(visitList);
    }

    @MutationMapping
    public AddVetPayload addVet(@Argument AddVetInput input) {
        try {
            Vet newVet = vetService.createVet(
                input.getFirstName(),
                input.getLastName(),
                input.getSpecialtyIds());

            return new AddVetSuccessPayload(newVet);
        } catch (InvalidVetDataException ex) {
            return new AddVetErrorPayload(ex.getLocalizedMessage());
        }
    }
}
