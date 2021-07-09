package org.springframework.samples.petclinic.visit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
public class VisitPublisher {

    private static final Logger logger = LoggerFactory.getLogger(VisitPublisher.class);

    private final Flux<Visit> publisher;
    private FluxSink<Visit> emitter;

    public VisitPublisher() {
        ConnectableFlux<Visit> publish = Flux.<Visit>create(fluxSink -> this.emitter = fluxSink)
            .publish();
        this.publisher = publish.autoConnect();
    }

    @TransactionalEventListener
    public void onNewVisit(VisitCreatedEvent event) {
        logger.info("onNewVisit {}", event);
        if (this.emitter != null) {
            this.emitter.next(event.getVisit());
        }
    }

    public Flux<Visit> getPublisher() {
        return this.publisher;
    }
}
