package org.springframework.samples.petclinic.graphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.samples.petclinic.auth.User;
import org.springframework.samples.petclinic.auth.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * EXAMPLE:
 * --------------------------
 *
 * - Access current principal in GraphQL handler functions by using the AuthenticationPrincipal annotation
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Controller
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryMapping
    public User me(@AuthenticationPrincipal(errorOnInvalidType = true) Jwt jwt) {
        String username = jwt.getSubject();
        log.info("JWT subject (username): '{}'", username);
        return userRepository.findByUsername(username).orElseThrow();
    }

    @QueryMapping
    public String ping() { return "pong"; }
}
