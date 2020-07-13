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

    @Column(name = "H_level")
    private float heirarchyLevel;

    @Column(name = "role_string")
    private String roleString;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public Role(String role, String roleString, float heirarchyLevel) {
        this.role = role;
        this.roleString = roleString;
        this.heirarchyLevel = heirarchyLevel;
    }

    public Role() {
    }

    public float getHeirarchyLevel() {
        return heirarchyLevel;
    }

    public void setHeirarchyLevel(float heirarchyLevel) {
        this.heirarchyLevel = heirarchyLevel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }

    public List<User> getUsers() {
        return users;
    }

}
