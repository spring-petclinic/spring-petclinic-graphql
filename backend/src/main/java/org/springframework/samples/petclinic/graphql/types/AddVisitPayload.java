package org.springframework.samples.petclinic.graphql.types;

import org.springframework.samples.petclinic.visit.Visit;

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
