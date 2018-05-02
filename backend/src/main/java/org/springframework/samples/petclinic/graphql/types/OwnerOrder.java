package org.springframework.samples.petclinic.graphql.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author exihaxi
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

    public static void main(String[] args) {
        OwnerOrder ins = new OwnerOrder();
        ins.setField(OrderField.firstName);
        ins.setOrder(OrderType.DESC);
        System.out.println(OrderType.DESC);
        System.out.println(ins.toString());
    }
}
