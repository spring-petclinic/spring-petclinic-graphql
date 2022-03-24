package org.springframework.samples.petclinic.graphql;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.samples.petclinic.auth.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * EXAMPLE:
 * --------------------------
 *
 * - Use 'plain' DataFetcher methods with Spring GraphQL (although Annotated Controllers might
 *   be the preferred way to write data fetchers now)
 */
@Component
public class AuthQueryWiring implements RuntimeWiringConfigurer {
    private static final Logger log = LoggerFactory.getLogger(AuthQueryWiring.class);

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", wiring -> wiring.dataFetcher("me", this::me));
    }

    private User me(DataFetchingEnvironment env) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            log.error("Principal not instanceof User {}", principal);
            return null;
        }

        return (User) principal;
    }
}
