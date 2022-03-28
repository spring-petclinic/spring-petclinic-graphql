package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Visit;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class VisitConnection {

    private final List<Visit> visits;

    public VisitConnection(List<Visit> visits) {
        this.visits = visits;
    }

    public int getTotalCount() {
        return visits.size();
    }

    public List<Visit> getVisits() {
        return visits;
    }
}
