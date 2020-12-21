package org.springframework.samples.petclinic.graphql.resolvers;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.samples.petclinic.graphql.types.VisitConnection;
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
