package org.springframework.samples.petclinic.auth.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.auth.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthQueryResolver implements GraphQLQueryResolver {
    private static final Logger log = LoggerFactory.getLogger(AuthQueryResolver.class);

    public User getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            log.error("Principal not instanceof User {}", principal);
            return null;
        }

        return (User) principal;
    }
}
