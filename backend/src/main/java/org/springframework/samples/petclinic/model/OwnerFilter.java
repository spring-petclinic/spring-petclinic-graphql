package org.springframework.samples.petclinic.model;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 * @author Nils Hartmann
 */
public class OwnerFilter implements Specification<Owner> {

    public static OwnerFilter NO_FILTER = new OwnerFilter() {
        public Predicate toPredicate(Root<Owner> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return null;
        }
    };

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public Predicate toPredicate(Root<Owner> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = new LinkedList<Predicate>();

        if (isSet(firstName)) {
            predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(
                    root.get("firstName")), firstName.toLowerCase() + "%"));
        }

        if (isSet(lastName)) {
            predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(
                    root.get("lastName")), lastName.toLowerCase() + "%"));
        }

        if (isSet(address)) {
            predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(
                    root.get("address")), address.toLowerCase() + "%"));
        }

        if (isSet(city)) {
            predicates.add(criteriaBuilder.equal(root.get("city"), city));
        }

        if (isSet(telephone)) {
            predicates.add(criteriaBuilder.equal(root.get("telephone"), telephone));
        }

        if (predicates.isEmpty()) {
            return null;
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private boolean isSet(String s) {
        return s != null && !s.isBlank();
    }

    @Override
    public String toString() {
        return "OwnerFilter{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", address='" + address + '\'' +
               ", city='" + city + '\'' +
               ", telephone='" + telephone + '\'' +
               '}';
    }
}
