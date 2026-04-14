package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

    public User() {}

    public User(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public Integer getId()              { return id; }
    public void setId(Integer id)       { this.id = id; }
    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }
    public boolean isActive()           { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "User{ id=" + id + ", name='" + name + "', active=" + active + " }";
    }
}
