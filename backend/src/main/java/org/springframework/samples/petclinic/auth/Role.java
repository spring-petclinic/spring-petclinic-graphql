package org.springframework.samples.petclinic.auth;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Taken from https://github.com/spring-petclinic/spring-petclinic-rest
 */
@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "role"}))
public class Role extends BaseEntity implements GrantedAuthority {

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Column(name = "role")
    private String name;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
