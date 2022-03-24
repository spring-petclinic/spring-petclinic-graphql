package org.springframework.samples.petclinic.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 * @author Nils Hartmann
 */
public class OwnerFilter implements Specification<Owner> {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;

    public static Optional<OwnerFilter> fromArgument(HashMap<String, String> argument) {
        if (argument == null) {
            return Optional.empty();
        }

        OwnerFilter ownerFilter = new OwnerFilter();
        ownerFilter.setFirstName(argument.get("firstName"));
        ownerFilter.setLastName(argument.get("lastName"));
        ownerFilter.setAddress(argument.get("address"));
        ownerFilter.setCity(argument.get("city"));
        ownerFilter.setTelephone(argument.get("telephone"));

        return Optional.of(ownerFilter);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "OwnerFilter{} " + super.toString();
    }

    @Override
    public Predicate toPredicate(Root<Owner> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicates = new LinkedList<Predicate>();

        if (isSet(firstName)) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
        }

        if (isSet(lastName)) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
        }

        if (isSet(address)) {
            predicates.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
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
}
