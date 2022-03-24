package org.springframework.samples.petclinic.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerFilter;
import org.springframework.samples.petclinic.model.OwnerOrder;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataOwnerRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Resolver for Owner Queries
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class OwnerQueryWiring implements RuntimeWiringConfigurer {
    private final OwnerRepository ownerRepository;

    public OwnerQueryWiring(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", wiring ->wiring
            .dataFetcher("owners", this::owners)
            .dataFetcher("owner", this::owner));
    }

    private OwnerSearchResult owners(DataFetchingEnvironment environment) {
        int pageNo = environment.getArgumentOrDefault("page", 0);
        int sizeNo = Math.min(environment.getArgumentOrDefault("size", 20), 25);

        Optional<OwnerFilter> filter = OwnerFilter.fromArgument(environment.getArgument("filter"));
        Optional<List<HashMap<String, String>>> ordersArg = Optional.ofNullable(environment.getArgument("orders"));
        // todo: we can map directly to 'Sort.order'
        List<OwnerOrder> orders = ordersArg.
            map(l -> l.stream()
                .map(OwnerOrder::fromArgument)
                .collect(Collectors.toList()))
            .orElse(null);


        Sort sort = orders != null ? Sort.by(orders.stream().map(OwnerOrder::toOrder).collect(Collectors.toList()))
            : Sort.unsorted();

        final PageRequest pageRequest = PageRequest.of(pageNo, sizeNo, sort);

        return new OwnerSearchResult(ownerRepository.findAll(filter.orElse(null), pageRequest));
    }

    private Owner owner(DataFetchingEnvironment env) {
        int id = env.getArgument("id");
        return ownerRepository.findById(id);
    }
}
