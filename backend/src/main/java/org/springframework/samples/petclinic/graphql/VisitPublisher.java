package org.springframework.samples.petclinic.graphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.VisitCreatedEvent;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

@Component
public class VisitPublisher {

    private static final Logger logger = LoggerFactory.getLogger(VisitPublisher.class);

    private final VisitRepository visitRepository;
    private final Sinks.Many<Visit> sink;

    public VisitPublisher(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
        this.sink = Sinks.many()
            .multicast()
            .onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onNewVisit(VisitCreatedEvent event) {

        Visit visit = visitRepository.findById(event.getVisitId());
        if (visit != null) {
            this.sink.emitNext(visit, Sinks.EmitFailureHandler.FAIL_FAST);
        }
    }

    public Flux<Visit> getPublisher() {
        return this.sink.asFlux();
    }
}
