package com.example.candidatetracker.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    @JsonIgnore
    private User recruiter;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name = "preferred_loc")
    private String preferredLoc;

    @Column(name = "ectc")
    private int ectc;

    @Column(name = "ctct")
    private int ctct;

    @Column (name = "source")
    private String source;

    @Column(name = "current_round")
    private int currentRound;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;

    @ManyToMany(mappedBy = "candidates")
    private Set<Skill> skillSet = new HashSet<>();

    public Candidate() {
    }

    public Candidate(String email, User recruiter, String firstName, String lastName, String contact, String address, String preferredLoc, int ectc, int ctct, String source) {
        this.email = email;
        this.recruiter = recruiter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.address = address;
        this.preferredLoc = preferredLoc;
        this.ectc = ectc;
        this.ctct = ctct;
        this.source = source;
    }

    public User getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(User recruiter) {
        this.recruiter = recruiter;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreferredLoc() {
        return preferredLoc;
    }

    public void setPreferredLoc(String preferredLoc) {
        this.preferredLoc = preferredLoc;
    }

    public int getEctc() {
        return ectc;
    }

    public void setEctc(int ectc) {
        this.ectc = ectc;
    }

    public int getCtct() {
        return ctct;
    }

    public void setCtct(int ctct) {
        this.ctct = ctct;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}