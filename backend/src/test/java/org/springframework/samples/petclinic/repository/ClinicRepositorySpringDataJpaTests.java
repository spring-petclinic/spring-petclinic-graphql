package org.springframework.samples.petclinic.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

/**
 * <p> Integration test using the 'Spring Data' profile.
 *
 * @author Michael Isvy
 * @see AbstractClinicRepositoryTests AbstractClinicRepositoryTests for more details. </p>
 */

@DataJpaTest
@ActiveProfiles(profiles = {"hsqldb"})
public class ClinicRepositorySpringDataJpaTests extends AbstractClinicRepositoryTests {

    @Autowired
    EntityManager entityManager;

    @Override
    protected void flush() {
        entityManager.flush();
        entityManager.clear();
    }
}
