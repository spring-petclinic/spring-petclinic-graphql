package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.graphql.types.OwnerFilter;
import org.springframework.samples.petclinic.graphql.types.OwnerOrder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 */
public class OwnerRepositoryCustomizationImpl implements OwnerRepositoryCustomization {

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
