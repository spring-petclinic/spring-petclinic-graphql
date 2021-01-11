package org.springframework.samples.petclinic.owner;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 * @author Nils Hartmann
 */
public class OwnerOrder {

    private OrderField field;
    private OrderType order;

    @JsonProperty("field")
    public OrderField getField() {
        return field;
    }

    public void setField(OrderField field) {
        this.field = field;
    }

    @JsonProperty("order")
    public OrderType getOrder() {
        return order;
    }

    public void setOrder(OrderType order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return this.field.toString() + " " + this.order.toString();
    }

    public Sort.Order toOrder() {
        Sort.Direction direction = order == null ? Sort.DEFAULT_DIRECTION : order == OrderType.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new Sort.Order(direction, field.name());
    }


}
