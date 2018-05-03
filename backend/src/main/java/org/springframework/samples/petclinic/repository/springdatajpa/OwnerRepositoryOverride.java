package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.graphql.types.OwnerFilter;
import org.springframework.samples.petclinic.graphql.types.OwnerOrder;
import org.springframework.samples.petclinic.model.Owner;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 *
 */
@Profile("spring-data-jpa")
public interface OwnerRepositoryOverride {

    public Collection<Owner> findAllByFilterOrder(OwnerFilter filter, List<OwnerOrder> orders);
}
