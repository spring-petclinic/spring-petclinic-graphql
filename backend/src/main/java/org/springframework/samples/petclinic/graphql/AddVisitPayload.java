package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Visit;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public record AddVisitPayload(Visit visit) {
}
