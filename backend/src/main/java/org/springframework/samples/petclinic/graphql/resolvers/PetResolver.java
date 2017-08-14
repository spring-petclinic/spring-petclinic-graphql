package org.springframework.samples.petclinic.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.samples.petclinic.graphql.types.VisitConnection;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetResolver implements GraphQLResolver<Pet> {
    public VisitConnection visits(Pet pet) {
        return new VisitConnection(pet.getVisits());
    }
}
