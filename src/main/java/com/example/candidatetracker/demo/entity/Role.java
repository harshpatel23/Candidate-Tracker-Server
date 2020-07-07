package com.example.candidatetracker.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role")
    private String role;

    @Column(name = "role_string")
    private String roleString;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    public String getRole() {
        return role;
    }

    public String getRoleString() {
        return roleString;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }

    public Role(String role, String roleString) {
        this.role = role;
        this.roleString = roleString;
    }

    public Role(){}

}
