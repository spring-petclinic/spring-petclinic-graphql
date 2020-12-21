package org.springframework.samples.petclinic.owner.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resolver for Owner Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class OwnerQueryResolver implements GraphQLQueryResolver {
    private final OwnerRepository ownerRepository;

    public OwnerQueryResolver(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> owners(OwnerFilter filter, List<OwnerOrder> orders) {
        return List.copyOf(ownerRepository.findAllByFilterOrder(filter, orders));
    }

    public Owner owner(int id) {
        return ownerRepository.findById(id);
    }


}
