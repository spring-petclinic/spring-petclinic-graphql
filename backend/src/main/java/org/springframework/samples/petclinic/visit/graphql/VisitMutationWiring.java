package org.springframework.samples.petclinic.visit.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class VisitMutationWiring implements RuntimeWiringBuilderCustomizer {
    private final static Logger logger = LoggerFactory.getLogger(VisitMutationWiring.class);

    private final VisitRepository visitRepository;
    private final PetRepository petRepository;

    public VisitMutationWiring(VisitRepository visitRepository, PetRepository petRepository) {
        this.visitRepository = visitRepository;
        this.petRepository = petRepository;
    }

    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder.type("Mutation", wiring -> wiring
            .dataFetcher("addVisit", this::addVisit)
        );
    }

    private AddVisitPayload addVisit(DataFetchingEnvironment env) {
        AddVisitInput addVisitInput = AddVisitInput.fromArgument(env.getArgument("input"));
        Pet pet = petRepository.findById(addVisitInput.getPetId());

        Visit visit = new Visit();
        visit.setDescription(addVisitInput.getDescription());
        visit.setPetId(pet.getId());
        visit.setDate(addVisitInput.getDate());
        addVisitInput.getVetId().ifPresent(visit::setVetId);

        visitRepository.save(visit);

        return new AddVisitPayload(visit);
    }

}
