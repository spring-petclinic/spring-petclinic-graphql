package org.springframework.samples.petclinic.domain.visit.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.domain.visit.VisitPublisher;
import org.springframework.stereotype.Component;

@Component
public class VisitSubscriptionWiring implements RuntimeWiringConfigurer {
    private static final Logger log = LoggerFactory.getLogger( VisitSubscriptionWiring.class );

    private final VisitPublisher visitPublisher;

    public VisitSubscriptionWiring(VisitPublisher visitPublisher) {
        this.visitPublisher = visitPublisher;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Subscription", wiring -> wiring.
            dataFetcher("onNewVisit", env -> {
                log.info("onNewVisit subscription!");
                return visitPublisher.getPublisher();
            }));
    }
}
