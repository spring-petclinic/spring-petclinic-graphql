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
    public PostgreSQLContainer<?> neo4jContainer() {
        return new PostgreSQLContainer<>("postgres:16.1").withReuse(true);
    }
}
