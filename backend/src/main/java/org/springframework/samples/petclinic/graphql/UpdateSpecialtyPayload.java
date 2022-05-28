package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Specialty;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public record UpdateSpecialtyPayload(Specialty specialty) {

}
