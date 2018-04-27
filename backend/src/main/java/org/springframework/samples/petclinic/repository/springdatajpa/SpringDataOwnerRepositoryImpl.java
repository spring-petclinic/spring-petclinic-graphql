package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.graphql.types.OwnerFilter;
import org.springframework.samples.petclinic.model.Owner;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 *
 */
@Profile("spring-data-jpa")
public class SpringDataOwnerRepositoryImpl implements OwnerRepositoryOverride {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Owner> findByFilter(OwnerFilter filter) {

        Query query = em.createQuery(filter.buildJpaQuery());
        
        filter.buildJpaQueryParameters(query);

        return query.getResultList();
    }
}
