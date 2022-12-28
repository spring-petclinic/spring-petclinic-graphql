package org.springframework.samples.petclinic.security;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.auth.UserRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


/**
 * Generates a non-expiring token for testing.
 *
 * 👮  👮  👮 YOU SHOULD NEVER DO THIS IN PRODUCTION 👮  👮  👮
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
class NeverExpiringTokenGenerator {

    private static final Logger log = LoggerFactory.getLogger(NeverExpiringTokenGenerator.class);

    private final UserRepository userRepository;
    private final JwtTokenService tokenService;

    NeverExpiringTokenGenerator(UserRepository userRepository, JwtTokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    /**
     * Creates a token that will never expire and will be stable accross re-starts
     * as longs as the RSAKey does not change (keys from publicKey and privateKey application properties)
     * <p>
     * This token can be used for easier testing using command line tools etc.
     * 👮  👮  👮 YOU SHOULD NEVER DO THIS IN 'REAL' PRODUCTION APPS 👮  👮  👮
     */
    @PostConstruct
    void createNonExpiringTokens() {
        var somewhen = Instant.now().plus(10 * 365, ChronoUnit.DAYS);

        var susi = userRepository.findByUsername("susi").orElseThrow();
        var neverExpiringManagerToken = tokenService.generateToken(susi.getUsername(), susi.getRoles(), somewhen);

        var joe = userRepository.findByUsername("joe").orElseThrow();
        var neverExpiringUserToken = tokenService.generateToken(joe.getUsername(), joe.getRoles(), somewhen);
        log.info("""

                ===============================================================
                🚨 🚨 🚨 NEVER EXPIRING JWT TOKENS 🚨 🚨 🚨
                ===============================================================
                SCOPE_MANAGER
                login: '{}'

                {"Authorization": "Bearer {}"}

                SCOPE_USER
                login: '{}'

                {"Authorization": "Bearer {}"}

                ===============================================================
                """,
            susi.getUsername(),
            neverExpiringManagerToken,
            joe.getUsername(),
            neverExpiringUserToken);
    }
}
