package org.springframework.samples.petclinic.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

/**
 * <p> Integration test using the jpa profile.
 *
 * @author Rod Johnson
 * @author Sam Brannen
 * @author Michael Isvy
 * @see AbstractClinicRepositoryTests AbstractClinicRepositoryTests for more details. </p>
 */

@SpringBootTest
@ActiveProfiles(profiles = {"jpa", "hsqldb"})
class ClinicRepositoryJpaTests extends AbstractClinicRepositoryTests {

    @Autowired
    EntityManager entityManager;

    @Override
    protected void flush() {
        entityManager.flush();
        entityManager.clear();
    }
}
