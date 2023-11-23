package org.springframework.samples.petclinic.graphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

import static org.springframework.samples.petclinic.model.OwnerFilter.NO_FILTER;

/**
 * GraphQL handler functions for Vet GraphQL type, Query and Mutation
 * <p>
 * Note that the addVet mutation is secured in the domain layer, so that only
 * users with SCOPE_MANAGER are allowed to create new vets
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
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
    public Window<Vet> vets(ScrollSubrange subrange) {
        var position = subrange.position().orElse(ScrollPosition.offset());
        var sort = Sort.by("lastName", "firstName");
        var limit = subrange.count().isPresent() ? Limit.of(subrange.count().getAsInt()) : Limit.unlimited();

        Window<Vet> vets = this.vetRepository.findBy(position, sort, limit);

        return vets;
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
