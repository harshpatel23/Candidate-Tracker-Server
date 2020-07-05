package com.example.candidatetracker.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contact")
    private String contact;

    @Column(name = "is_active")
    private int isActive;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
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

    public User(String email, String password, String firstName, String lastName, String contact) {
        this.email = email;
        this.password = password;
        //this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        //this.manager = manager;
    }

}
