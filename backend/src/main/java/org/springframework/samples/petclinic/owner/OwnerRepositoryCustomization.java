package org.springframework.samples.petclinic.owner;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.graphql.types.OwnerFilter;
import org.springframework.samples.petclinic.graphql.types.OwnerOrder;

import java.util.Collection;
import java.util.List;

/**
 * Custom functions for OwnerRepository
 *
 * @author Xiangbin HAN (hanxb2001@163.com)
 * @author Nils Hartmann
 */
public interface OwnerRepositoryCustomization {
    /**
     * Retrieve <code>Owner</code>s from the data store with optional filter or order, returning retrieved owners
     *
     * @return a <code>Collection</code> of <code>Owner</code>s (or an empty <code>Collection</code> if none
     * found)
     */
    Collection<Owner> findAllByFilterOrder(OwnerFilter filter, List<OwnerOrder> orders) throws DataAccessException;

}
