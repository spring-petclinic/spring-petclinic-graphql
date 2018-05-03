package org.springframework.samples.petclinic.graphql.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Query;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Xiangbin HAN (hanxb2001@163.com)
 *
 */
public class OwnerFilter {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("telephone")
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

    /**
     * @return String
     */
    public String buildJpaQuery() {
        Optional<OwnerFilter> nonNullFilter = Optional.ofNullable(this);
        StringBuilder sb = new StringBuilder(" WHERE");
        sb.append(
            nonNullFilter.map(f -> f.getFirstName())
                .map(item -> " owner.firstName LIKE :firstName and")
                .orElse("")
        );
        sb.append(
                nonNullFilter.map(f -> f.getLastName())
                    .map(item -> " owner.lastName LIKE :lastName and")
                    .orElse("")
            );
        sb.append(
                nonNullFilter.map(f -> f.getAddress())
                    .map(item -> " owner.address LIKE :address and")
                    .orElse("")
            );
        sb.append(
                nonNullFilter.map(f -> f.getCity())
                    .map(item -> " owner.city = :city and")
                    .orElse("")
            );
        sb.append(
                nonNullFilter.map(f -> f.getTelephone())
                    .map(item -> " owner.telephone = :telephone and")
                    .orElse("")
            );

        if(sb.indexOf(" and") > 0)
            return sb.substring(0, sb.lastIndexOf(" and"));
        else
            return "";
        
    }

    /**
     * @param query
     */
    public void buildJpaQueryParameters(Query query) {
        
        Optional<OwnerFilter> nonNullFilter = Optional.ofNullable(this);
        
        nonNullFilter.map(f -> f.getFirstName()).ifPresent(item -> query.setParameter("firstName", item + "%"));
        nonNullFilter.map(f -> f.getLastName()).ifPresent(item -> query.setParameter("lastName", item + "%"));
        nonNullFilter.map(f -> f.getAddress()).ifPresent(item -> query.setParameter("address", "%" + item + "%"));
        nonNullFilter.map(f -> f.getCity()).ifPresent(item -> query.setParameter("city", item));
        nonNullFilter.map(f -> f.getTelephone()).ifPresent(item -> query.setParameter("telephone", item));
        
    }

    /**
     * @return String
     */
    public String buildJdbcQuery() {
        Optional<OwnerFilter> nonNullFilter = Optional.ofNullable(this);
        StringBuilder sb = new StringBuilder(" WHERE");
        sb.append(
            nonNullFilter.map(f -> f.getFirstName())
                .map(item -> " first_name LIKE :firstName and")
                .orElse("")
        );
        sb.append(
                nonNullFilter.map(f -> f.getLastName())
                    .map(item -> " last_name LIKE :lastName and")
                    .orElse("")
            );
        sb.append(
                nonNullFilter.map(f -> f.getAddress())
                    .map(item -> " address LIKE :address and")
                    .orElse("")
            );
        sb.append(
                nonNullFilter.map(f -> f.getCity())
                    .map(item -> " city = :city and")
                    .orElse("")
            );
        sb.append(
                nonNullFilter.map(f -> f.getTelephone())
                    .map(item -> " telephone = :telephone and")
                    .orElse("")
            );

        if(sb.indexOf(" and") > 0)
            return sb.substring(0, sb.lastIndexOf(" and"));
        else
            return "";
        
    }

    /**
     * @param params
     */
    public void buildJdbcQueryParameters(Map<String, Object> params) {
        
        Optional<OwnerFilter> nonNullFilter = Optional.ofNullable(this);
        
        nonNullFilter.map(f -> f.getFirstName()).ifPresent(item -> params.put("firstName", item + "%"));
        nonNullFilter.map(f -> f.getLastName()).ifPresent(item -> params.put("lastName", item + "%"));
        nonNullFilter.map(f -> f.getAddress()).ifPresent(item -> params.put("address", "%" + item + "%"));
        nonNullFilter.map(f -> f.getCity()).ifPresent(item -> params.put("city", item));
        nonNullFilter.map(f -> f.getTelephone()).ifPresent(item -> params.put("telephone", item));
    }
}
