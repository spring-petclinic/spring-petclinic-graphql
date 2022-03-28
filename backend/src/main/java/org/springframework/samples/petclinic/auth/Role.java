package org.springframework.samples.petclinic.auth;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Taken from https://github.com/spring-petclinic/spring-petclinic-rest
 */
@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "role"}))
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

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

    public String toString() {
        return "Role, name='" + this.name + "'";
    }

}
