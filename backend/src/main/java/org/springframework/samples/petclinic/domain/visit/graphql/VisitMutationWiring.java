package org.springframework.samples.petclinic.domain.visit.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.domain.visit.Visit;
import org.springframework.samples.petclinic.domain.visit.VisitService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class VisitMutationWiring implements RuntimeWiringConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(VisitMutationWiring.class);

    private final VisitService visitService;

    public VisitMutationWiring(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Mutation", wiring -> wiring
            .dataFetcher("addVisit", this::addVisit)
        );
    }

    private AddVisitPayload addVisit(DataFetchingEnvironment env) {
        Map<String, Object> input = env.getArgument("input");

        Visit visit = visitService.addVisit(
            (int)input.get("petId"),
            (String) input.get("description"),
            (LocalDate) input.get("date"),
            Optional.ofNullable((Integer)input.get("vetId"))
        );

        return new AddVisitPayload(visit);
    }
}
