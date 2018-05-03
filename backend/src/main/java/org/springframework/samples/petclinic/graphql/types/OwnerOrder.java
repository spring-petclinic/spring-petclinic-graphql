package org.springframework.samples.petclinic.graphql.types;

import java.util.List;
import java.util.Optional;

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

    /**
     * @param orders
     * @return String
     */
    public static String buildOrderJdbcQuery(List<OwnerOrder> orders) {
        StringBuilder sb = new StringBuilder();

        Optional<List<OwnerOrder>> nonNullOrders = Optional.ofNullable(orders);
        nonNullOrders.ifPresent(list -> {
            sb.append(" order by");
            list.forEach(order -> {
                if (order.getField().equals(OrderField.firstName))
                    sb.append(" first_name " + order.getOrder() + ",");
                else if(order.getField().equals(OrderField.lastName))
                    sb.append(" last_name " + order.getOrder() + ",");
                else
                    sb.append(" " + order.getField() + " " + order.getOrder() + ",");
            });
        });

        if(sb.indexOf("order by") > 0)
            return sb.substring(0, sb.lastIndexOf(","));
        else
            return "";
    }

    /**
     * @param orders
     * @return String
     */
    public static String buildOrderJpaQuery(List<OwnerOrder> orders) {
        StringBuilder sb = new StringBuilder();

        Optional<List<OwnerOrder>> nonNullOrders = Optional.ofNullable(orders);
        nonNullOrders.ifPresent(list -> 
            {sb.append(" order by");
            list.forEach(order -> sb.append(" owner." + order.getField() + " " + order.getOrder() + ","));}
        );

        if(sb.indexOf("order by") > 0)
            return sb.substring(0, sb.lastIndexOf(","));
        else
            return "";
    }
}
