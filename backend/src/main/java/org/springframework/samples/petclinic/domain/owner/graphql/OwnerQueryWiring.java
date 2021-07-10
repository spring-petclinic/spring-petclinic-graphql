package org.springframework.samples.petclinic.domain.owner.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.samples.petclinic.domain.owner.*;
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
public class OwnerQueryWiring implements RuntimeWiringBuilderCustomizer {
    private final OwnerRepository ownerRepository;

    public OwnerQueryWiring(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void customize(RuntimeWiring.Builder builder) {
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
