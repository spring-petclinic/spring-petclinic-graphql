/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.lang.Nullable;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.OwnerFilter;
import org.springframework.samples.petclinic.model.OwnerOrder;

/**
 * Repository class for <code>Owner</code> domain objects
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 * @author Nils Hartmann
 */
public interface OwnerRepository extends Repository<Owner, Integer> {

    /**
     * Retrieve <code>Owner</code>s from the data store by last name, returning all owners whose last name <i>starts</i>
     * with the given name.
     *
     * @param lastName Value to search for
     * @return a <code>Collection</code> of matching <code>Owner</code>s (or an empty <code>Collection</code> if none
     * found)
     */
    @Query("SELECT DISTINCT owner FROM Owner owner WHERE owner.lastName LIKE :lastName%")
    Collection<Owner> findByLastName(String lastName);

    /**
     * Retrieve an <code>Owner</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>Owner</code> if found
     */
    Optional<Owner> findById(Integer id);


    /**
     * Save an <code>Owner</code> to the data store, either inserting or updating it.
     *
     * @param owner the <code>Owner</code> to save
     * @see BaseEntity#isNew
     */
    void save(Owner owner);

    /**
     * Retrieve <code>Owner</code>s from the data store, returning all owners
     *
     * @return a <code>Collection</code> of <code>Owner</code>s (or an empty <code>Collection</code> if none
     * found)
     */
	Collection<Owner> findAll();

    /**
     * Delete an <code>Owner</code> to the data store by <code>Owner</code>.
     *
     * @param owner the <code>Owner</code> to delete
     *
     */
	void delete(Owner owner);

    Page<Owner> findAll(@Nullable Specification<Owner> spec, Pageable pageable);
}
