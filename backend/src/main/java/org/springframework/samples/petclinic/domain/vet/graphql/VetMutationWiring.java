package org.springframework.samples.petclinic.domain.vet.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.domain.vet.InvalidVetDataException;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.domain.vet.VetService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 * @author Nils Hartmann
 */
@Component
public class VetMutationWiring implements RuntimeWiringConfigurer {

    private static final Logger log = LoggerFactory.getLogger(VetMutationWiring.class);

    private final VetService vetService;

    public VetMutationWiring(VetService vetService) {
        this.vetService = vetService;
    }

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Mutation", wiring -> wiring
            .dataFetcher("addVet", this::addVet)
        );
    }

    private AddVetPayload addVet(DataFetchingEnvironment env) {
        AddVetInput input = AddVetInput.fromArgument(env.getArgument("input"));
        try {
            Vet newVet = vetService.createVet(
                input.getFirstName(),
                input.getLastName(),
                input.getSpecialtyIds());

            return new AddVetSuccessPayload(newVet);
        } catch (InvalidVetDataException ex) {
            return new AddVetErrorPayload(ex.getLocalizedMessage());
        }
    }
}
