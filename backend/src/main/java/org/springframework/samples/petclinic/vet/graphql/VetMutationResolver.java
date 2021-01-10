package org.springframework.samples.petclinic.vet.graphql;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.vet.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nils Hartmann
 */
@Component
public class VetMutationResolver implements GraphQLMutationResolver {

    private static final Logger log = LoggerFactory.getLogger(VetMutationResolver.class);

    private final VetService vetService;

    public VetMutationResolver(VetService vetService) {
        this.vetService = vetService;
    }

    public AddVetPayload addVet(AddVetInput input) {
        try {
            Vet newVet = vetService.createVet(
                input.getFirstName(),
                input.getLastName(),
                input.getSpecialtyIds());

            return new AddVetSuccessPayload(newVet);
        } catch (InvalidVetDataException ex) {
            return new AddVetErrorPayload(ex.getLocalizedMessage());
        } catch (AccessDeniedException ex) {
            return new AddVetErrorPayload("You're not allowed to add new Vets");
        }
    }
}
