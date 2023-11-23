package org.springframework.samples.petclinic.model;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 * @author Nils Hartmann
 */
public record OwnerOrder(OrderField field, Sort.Direction direction) {

    public static List<Sort.Order> defaultOrder() {
        return List.of(Sort.Order.asc("id"));
    }

    public Sort.Order toSortOrder() {
//        Sort.Direction direction = order == null ? Sort.DEFAULT_DIRECTION : order == OrderDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new Sort.Order(direction, field.name());
    }

    public Sort toSort() {
        return Sort.by(direction, field.name());
    }

}
