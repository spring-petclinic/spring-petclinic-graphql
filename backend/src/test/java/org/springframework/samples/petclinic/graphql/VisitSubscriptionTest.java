package org.springframework.samples.petclinic.graphql;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.WebGraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.samples.petclinic.PetClinicTestDbConfiguration;
import org.springframework.samples.petclinic.model.VisitService;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Optional;

import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(PetClinicTestDbConfiguration.class)
public class VisitSubscriptionTest extends GraphQlTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(VisitSubscriptionTest.class);

    @LocalServerPort
    int port;

    @Autowired
    VisitService visitService;

    @Test
    void onNewVisit_for_new_visits(@Autowired GraphQlProperties graphQlProperties) {
        String url = format("http://localhost:%s/%s?access_token=%s",
            port,
            graphQlProperties.getWebsocket().getPath(),
            createUserToken());

        // https://docs.spring.io/spring-graphql/reference/testing.html#testing.subscriptions
        WebSocketClient client = new ReactorNettyWebSocketClient();
        WebSocketGraphQlTester tester = WebSocketGraphQlTester.builder(url, client).build();

        Flux<GraphQlTester.Response> visitSubscription = tester.
            // language=GraphQL
                document("""
                    subscription {
                        onNewVisit {
                            id description pet { id } treatingVet { id }
                        }
                    }
                """)
            .executeSubscription()
            .toFlux();  //

        var newVisit = visitService.addVisit(2,
            "New Visit for Subscription",
            LocalDate.now(),
            Optional.of(1));

        StepVerifier.
            create(visitSubscription)
            .consumeNextWith(r -> {
                r.path("onNewVisit.id").entity(Integer.class).isEqualTo(newVisit.getId())
                    .path("onNewVisit.description").entity(String.class).isEqualTo("New Visit for Subscription")
                    .path("onNewVisit.pet.id").entity(Integer.class).isEqualTo(2)
                    .path("onNewVisit.treatingVet.id").entity(Integer.class).isEqualTo(1);
            }).thenCancel().verify();
    }
}
