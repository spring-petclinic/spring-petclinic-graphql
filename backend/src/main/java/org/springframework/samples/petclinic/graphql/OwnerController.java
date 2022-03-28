package org.springframework.samples.petclinic.graphql;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerFilter;
import org.springframework.samples.petclinic.model.OwnerOrder;
import org.springframework.samples.petclinic.model.OwnerService;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * GraphQL handler functions for Ower type, Query and Mutation
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class OwnerController {

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
    public OwnerSearchResult owners(@Argument Optional<Integer> page, @Argument Optional<Integer>size,
                                    @Argument Optional<OwnerFilter> filter,
                                    @Argument List<OwnerOrder> orders) {
        int pageNo = page.orElse(0);
        int sizeNo = Math.min(size.orElse(20), 25);

        Sort sort = orders != null ? Sort.by(orders.stream().map(OwnerOrder::toOrder).collect(Collectors.toList()))
            : Sort.unsorted();

//
        final PageRequest pageRequest = PageRequest.of(pageNo, sizeNo, sort);
//
        return new OwnerSearchResult(ownerRepository.findAll(filter.orElse(null), pageRequest));
    }

    @QueryMapping
    public Owner owner(DataFetchingEnvironment env) {
        int id = env.getArgument("id");
        return ownerRepository.findById(id);
    }




}
