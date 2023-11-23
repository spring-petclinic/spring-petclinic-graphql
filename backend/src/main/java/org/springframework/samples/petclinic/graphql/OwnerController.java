package org.springframework.samples.petclinic.graphql;

import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerFilter;
import org.springframework.samples.petclinic.model.OwnerOrder;
import org.springframework.samples.petclinic.model.OwnerService;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

import static org.springframework.samples.petclinic.model.OwnerFilter.NO_FILTER;

/**
 * GraphQL handler functions for Ower type, Query and Mutation
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class OwnerController {

    private static final Logger log = LoggerFactory.getLogger(OwnerController.class);

    private final OwnerService ownerService;
    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerService ownerService, OwnerRepository ownerRepository) {
        this.ownerService = ownerService;
        this.ownerRepository = ownerRepository;
    }

    @MutationMapping
    public AddOwnerPayload addOwner(@Argument AddOwnerInput input) {
        Owner owner = ownerService.addOwner(
            input.getFirstName(),
            input.getLastName(),
            input.getTelephone(),
            input.getAddress(),
            input.getCity()
        );

        return new AddOwnerPayload(owner);
    }

    @MutationMapping
    public UpdateOwnerPayload updateOwner(@Argument UpdateOwnerInput input) {
        Owner owner = ownerService.updateOwner(
            input.getOwnerId(),
            input.getFirstName(),
            input.getLastName(),
            input.getTelephone(),
            input.getAddress(),
            input.getCity()
        );
        return new UpdateOwnerPayload(owner);
    }

    @QueryMapping
    public Window<Owner> owners(ScrollSubrange subrange,
                                @Argument Optional<OwnerFilter> filter,
                                @Argument Optional<List<OwnerOrder>> order) {
        // Get position represented by this subrange or (if none yet)
        //   create a default Offset-based ScrollPosition
        var position = subrange.position().orElse(ScrollPosition.offset());

        // If user does not specify a count (with first or last), throw Exception
        //  note that it is currently not possible to express a union type-like thing
        //  (first OR last must be present) as input parameters
        var limit = subrange.count().orElseThrow(() -> new IllegalArgumentException("Please specify either 'first' or 'last'"));

        var orders = order.map(l -> l.stream().map(OwnerOrder::toSortOrder).toList()).orElse(List.of());
        var sort = Sort.by(orders.isEmpty() ? OwnerOrder.defaultOrder() : orders);

        // Note that the returned Window is automatically mapped
        //  to the according GraphQL types by Spring for GraphQL
        Window<Owner> owners = this.ownerRepository.findBy(filter.orElse(NO_FILTER), query -> query.
            limit(limit)
            .sortBy(sort)
            .scroll(position)
        );

        return owners;
    }

    @QueryMapping
    public Optional<Owner> owner(DataFetchingEnvironment env) {
        int id = env.getArgument("id");
        return ownerRepository.findById(id);
    }
}
