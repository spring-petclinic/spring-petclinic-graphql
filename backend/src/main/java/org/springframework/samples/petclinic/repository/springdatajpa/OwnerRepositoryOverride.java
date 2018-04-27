package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.graphql.types.OwnerFilter;
import org.springframework.samples.petclinic.model.Owner;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 *
 */
@Profile("spring-data-jpa")
public interface OwnerRepositoryOverride {

    public Collection<Owner> findByFilter(OwnerFilter filter);

}
