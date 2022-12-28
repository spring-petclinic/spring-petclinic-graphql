package org.springframework.samples.petclinic.security;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login via Username/Password via HTTP POST endpoint.
 * <p>
 * - The /graphql endpoints requires a valid token. This token can be requests by invoking
 * HTTP "POST /login with" sending username and password.
 * <p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final JwtTokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtTokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("Authorizing '{}'", request.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = tokenService.generateToken(authentication);

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception ex) {
            log.error("could not authenticate: " + ex, ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
