package org.springframework.samples.petclinic.owner.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public OwnerSearchResult owners(Integer page, Integer size, Optional<OwnerFilter> filter, List<OwnerOrder> orders) {
        Sort sort = orders != null ? Sort.by(orders.stream().map(OwnerOrder::toOrder).collect(Collectors.toList()))
            : Sort.unsorted();
        int pageNo = Optional.ofNullable(page).orElse(0);
        int sizeNo = Optional.ofNullable(size).map(s -> Math.min(s, 25)).orElse(20);

        final PageRequest pageRequest = PageRequest.of(pageNo, sizeNo, sort);

        return new OwnerSearchResult(ownerRepository.findAll(filter.orElse(null), pageRequest));
    }

    public Owner owner(int id) {
        return ownerRepository.findById(id);
    }
}
