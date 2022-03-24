package org.springframework.samples.petclinic.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
@Component
public class OwnerMutationWiring implements RuntimeWiringConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(OwnerMutationWiring.class);

    private final OwnerService ownerService;

    public OwnerMutationWiring(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Mutation", wiring -> wiring
            .dataFetcher("addOwner", this::addOwner)
            .dataFetcher("updateOwner", this::updateOwner)
        );
    }

    public AddOwnerPayload addOwner(DataFetchingEnvironment env) {
        Map<String, String> ownerInput = env.getArgument("input");
        Owner owner = ownerService.addOwner(
            ownerInput.get("firstName"),
            ownerInput.get("lastName"),
            ownerInput.get("telephone"),
            ownerInput.get("address"),
            ownerInput.get("city")
        );

        return new AddOwnerPayload(owner);
    }

    public UpdateOwnerPayload updateOwner(DataFetchingEnvironment env) {
        Map<String, ?> ownerInput = env.getArgument("input");

        Owner owner = ownerService.updateOwner(
            (int) ownerInput.get("ownerId"),
            (String)ownerInput.get("firstName"),
            (String)ownerInput.get("lastName"),
            (String)ownerInput.get("telephone"),
            (String)ownerInput.get("address"),
            (String)ownerInput.get("city")
        );

        return new UpdateOwnerPayload(owner);
    }

}
