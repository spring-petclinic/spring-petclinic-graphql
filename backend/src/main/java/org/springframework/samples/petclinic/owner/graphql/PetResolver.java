package org.springframework.samples.petclinic.owner.graphql;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.samples.petclinic.visit.graphql.VisitConnection;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class
PetResolver implements GraphQLResolver<Pet> {
    public VisitConnection visits(Pet pet) {
        return new VisitConnection(pet.getVisits());
    }
}
