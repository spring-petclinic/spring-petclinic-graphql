package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Visit;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AddVisitPayload {
    private final Visit visit;

    public AddVisitPayload(Visit visit) {
        this.visit = visit;
    }

    public Visit getVisit() {
        return visit;
    }
}
