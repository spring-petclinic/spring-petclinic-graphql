package org.springframework.samples.petclinic.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class PingGraphQLQueryResolver implements GraphQLQueryResolver {

    public String ping() {
        return "pong";
    }

}
