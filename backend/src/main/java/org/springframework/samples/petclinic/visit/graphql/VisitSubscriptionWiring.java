package org.springframework.samples.petclinic.visit.graphql;

import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.visit.VisitPublisher;
import org.springframework.stereotype.Component;

@Component
public class VisitSubscriptionWiring implements RuntimeWiringBuilderCustomizer {
    private static final Logger log = LoggerFactory.getLogger( VisitSubscriptionWiring.class );

    private final VisitPublisher visitPublisher;

    public VisitSubscriptionWiring(VisitPublisher visitPublisher) {
        this.visitPublisher = visitPublisher;
    }

    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder.type("Subscription", wiring -> wiring.
            dataFetcher("onNewVisit", env -> {
                log.info("onNewVisit subscription!");
                return visitPublisher.getPublisher();
            }));
    }
}
