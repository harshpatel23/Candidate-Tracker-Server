package com.example.candidatetracker.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                  property = "email")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "r_id")
    private Role role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contact")
    private String contact;

    @Column(name = "is_active")
    private int isActive;

    @ManyToOne
    // @JsonIgnore
    @JoinColumn(name = "manager_id")
    private User manager;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }


    public Set<User> getSuccessors() {
        return successors;
    }

    public void setSuccessors(Set<User> successors) {
        this.successors = successors;
    }

    @JsonIgnore
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="user_closure",
            joinColumns={@JoinColumn(name="parent_id")},
            inverseJoinColumns={@JoinColumn(name="child_id")})
    private Set<User> successors = new HashSet<>();

    public Set<User> getManagers() {
        return managers;
    }

    public void setManagers(Set<User> managers) {
        this.managers = managers;
    }

    @JsonIgnore
    @ManyToMany(mappedBy="successors")
    private Set<User> managers = new HashSet<>();

    public void setSubordinates(List<User> subordinates) {
        this.subordinates = subordinates;
    }

    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> subordinates = new ArrayList<>();

    public User() {
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContact() {
        return contact;
    }

    public User getManager() {
        return manager;
    }

    public List<User> getSubordinates() {
        return subordinates;
    }

    public User(String email, String password, Role role, String firstName, String lastName, String contact, User manager, int isActive) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.manager = manager;
        this.isActive = isActive;
    }

}
