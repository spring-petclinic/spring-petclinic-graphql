package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerFilter;
import org.springframework.samples.petclinic.model.OwnerOrder;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 *
 */
public class SpringDataOwnerRepositoryImpl implements OwnerRepositoryOverride {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Collection<Owner> findAllByFilterOrder(OwnerFilter filter, List<OwnerOrder> orders) {
        StringBuilder sb = new StringBuilder("SELECT owner FROM Owner owner");

        Optional<OwnerFilter> nonNullFilter = Optional.ofNullable(filter);
        nonNullFilter.ifPresent(f -> sb.append(f.buildJpaQuery()));

        sb.append(OwnerOrder.buildOrderJpaQuery(orders));

        Query query = this.em.createQuery(sb.toString());
        nonNullFilter.ifPresent(f -> f.buildJpaQueryParameters(query));

        return query.getResultList();
    }
}
