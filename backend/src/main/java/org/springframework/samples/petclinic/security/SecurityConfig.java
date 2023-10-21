package org.springframework.samples.petclinic.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.samples.petclinic.auth.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configures security for PetClinic. Ensures that all requests to /graphql are secured.
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true) // Enable @PreAuthorize method-level security
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final RSAKey rsaKey;

    public SecurityConfig(RSAKeyProvider RSAKeyProvider) {
        this.rsaKey = RSAKeyProvider.getRsaKey();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    username
                )
            );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // for h2 explorer
        http.headers().frameOptions().sameOrigin();


        http.authorizeHttpRequests(authorizeHttpRequests ->
            authorizeHttpRequests
                .shouldFilterAllDispatcherTypes(false)
                // allow login
                .requestMatchers("/login/**").permitAll()
//                // allow access to graphiql
                .requestMatchers("/").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/s.html").permitAll()
                .requestMatchers("/index.html").permitAll()
                .requestMatchers("/graphiql/**").permitAll()
                // ...while all other endpoints (INCLUDING /graphql !) should be authenticated
                //    fine granular, Role-based, access checks are done in the resolver
                .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKeyProvider RSAKeyProvider) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(Environment env) {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        String allowedOrigins = env.getProperty("PETCLINIC_ALLOWED_ORIGINS", "http://localhost:[*]");


        Arrays.stream(allowedOrigins.split(","))
            .forEach(origin -> {
                log.info("Allowing CORS Origin Pattern '{}'", origin);
                config.addAllowedOriginPattern(origin);
            });

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
