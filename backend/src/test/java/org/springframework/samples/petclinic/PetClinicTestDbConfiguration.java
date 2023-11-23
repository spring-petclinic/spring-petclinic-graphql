package org.springframework.samples.petclinic;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@SuppressWarnings("ALL")
@TestConfiguration(proxyBeanMethods = false)
public class PetClinicTestDbConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:16.1-alpine")
            // https://stackoverflow.com/a/74095511/6134498
            .withEnv("POSTGRES_INITDB_ARGS", "--locale-provider=icu --icu-locale=en");
    }
}
