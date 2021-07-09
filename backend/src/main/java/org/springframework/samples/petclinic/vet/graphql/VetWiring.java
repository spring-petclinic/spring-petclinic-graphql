package org.springframework.samples.petclinic.vet.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.samples.petclinic.visit.graphql.VisitConnection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VetWiring implements RuntimeWiringBuilderCustomizer {
    private final VisitRepository visitRepository;

    public VetWiring(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public void customize(RuntimeWiring.Builder builder) {
        builder.type("Vet", wiring -> wiring.dataFetcher("visits", this::visits));
    }

    private VisitConnection visits(DataFetchingEnvironment env) {
        Vet vet = env.getSource();
        List<Visit> visitList = visitRepository.findByVetId(vet.getId());
        return new VisitConnection(visitList);
    }
}
