package org.springframework.samples.petclinic.graphql.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 *
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
}
