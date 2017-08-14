package org.springframework.samples.petclinic.graphql.resolvers;

import graphql.GraphQLError;
import graphql.servlet.DefaultGraphQLErrorHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class PetClinicGraphQLErrorHandler extends DefaultGraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return super.processErrors(errors);
    }
}
