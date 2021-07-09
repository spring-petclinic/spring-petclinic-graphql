package org.springframework.samples.petclinic.owner;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 * @author Nils Hartmann
 */
public class OwnerOrder {

    private OrderField field;
    private OrderType order;

    public static OwnerOrder fromArgument(HashMap<String, String> argument) {
        OwnerOrder ownerOrder = new OwnerOrder();
        ownerOrder.setField(OrderField.valueOf(argument.get("field")));

        if (argument.containsKey("order")) {
            ownerOrder.setOrder(OrderType.valueOf(argument.get("order")));
        }

        return ownerOrder;
    }


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
        return this.field + " " + this.order;
    }

    public Sort.Order toOrder() {
        Sort.Direction direction = order == null ? Sort.DEFAULT_DIRECTION : order == OrderType.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        return new Sort.Order(direction, field.name());
    }


}
