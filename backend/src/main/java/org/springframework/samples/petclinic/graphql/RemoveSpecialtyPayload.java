package org.springframework.samples.petclinic.graphql;

import org.springframework.samples.petclinic.model.Specialty;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public record RemoveSpecialtyPayload(List<Specialty> specialties) {

}
