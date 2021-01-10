package org.springframework.samples.petclinic.vet.graphql;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.samples.petclinic.visit.graphql.VisitConnection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VetResolver implements GraphQLResolver<Vet> {
    private final VisitRepository visitRepository;


    public VetResolver(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public VisitConnection visits(Vet vet) {
        List<Visit> visitList = visitRepository.findByVetId(vet.getId());

        return new VisitConnection(visitList);
    }

}
