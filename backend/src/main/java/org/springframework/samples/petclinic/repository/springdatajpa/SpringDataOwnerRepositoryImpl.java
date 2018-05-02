package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.graphql.types.OwnerFilter;
import org.springframework.samples.petclinic.graphql.types.OwnerOrder;
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

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Owner> findAllByOrders(List<OwnerOrder> orders) {

        Optional<List<OwnerOrder>> nonNullOrders = Optional.ofNullable(orders);
        StringBuilder sb = new StringBuilder("SELECT owner FROM Owner owner");
        nonNullOrders.ifPresent(list -> 
            {sb.append(" order by");
            list.forEach(order -> sb.append(" owner." + order.getField() + " " + order.getOrder() + ","));}
        );

        Query query;
        if(sb.indexOf("order by") > 0)
            query = em.createQuery(sb.substring(0, sb.lastIndexOf(",")));
        else
            query = em.createQuery(sb.toString());

        return query.getResultList();
    }
}
