package org.springframework.samples.petclinic.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.VisitService;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class VisitController {

    private final VisitService visitService;
    private final VisitPublisher visitPublisher;


    public VisitController(VisitService visitService, VisitPublisher visitPublisher) {
        this.visitService = visitService;
        this.visitPublisher = visitPublisher;
    }

    @MutationMapping
    public AddVisitPayload addVisit(@Argument AddVisitInput input) {
        Visit visit = visitService.addVisit(
            input.getPetId(),
            input.getDescription(),
            input.getDate(),
            input.getVetId()
        );

        return new AddVisitPayload(visit);
    }

    @SubscriptionMapping
    public Flux<Visit> onNewVisit(){
        return visitPublisher.getPublisher();
    }

}
