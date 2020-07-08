package com.example.candidatetracker.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Entity
@DynamicUpdate
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
    @JoinColumn(name = "manager_id")
    @JsonIgnoreProperties({"managers", "successors", "subordinates", "manager"})
    @JsonIgnore
    private User manager;

    @OneToMany(mappedBy = "recruiter")
    private Set<Candidate> candidates = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_closure",
            joinColumns = {@JoinColumn(name = "parent_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")})
    @JsonIgnoreProperties({"managers", "successors", "subordinates", "manager"})
    @JsonIgnore
    private Set<User> successors = new HashSet<>();

    @ManyToMany(mappedBy = "successors")
    @JsonIgnoreProperties({"managers", "successors", "subordinates", "manager"})
    @JsonIgnore
    private Set<User> managers = new HashSet<>();

    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"managers", "successors", "subordinates", "manager"})
    @JsonIgnore
    private List<User> subordinates = new ArrayList<>();

    @OneToMany(mappedBy = "interviewer")
    @JsonIgnore
    private Set<Interview> interviews = new HashSet<>();

    @OneToMany(mappedBy = "updatedBy")
    @JsonIgnore
    private List<Interview> interviewFeedbackUpdates = new ArrayList<>();

    @ManyToMany(mappedBy = "interviewers")
    @JsonIgnore
    private Set<Skill> skills = new HashSet<>();

    public User() {
    }

    public User(String email, Role role, String firstName, String lastName, String contact, User manager) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.manager = manager;
        this.isActive = 1;
        this.password = "Asasasa";
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(Set<Interview> interviews) {
        this.interviews = interviews;
    }

    public List<Interview> getInterviewFeedbackUpdates() {
        return interviewFeedbackUpdates;
    }

    public void setInterviewFeedbackUpdates(List<Interview> interviewFeedbackUpdates) {
        this.interviewFeedbackUpdates = interviewFeedbackUpdates;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<User> getSuccessors() {
        return successors;
    }

    public void setSuccessors(Set<User> successors) {
        this.successors = successors;
    }

    public Set<User> getManagers() {
        return managers;
    }

    public void setManagers(Set<User> managers) {
        this.managers = managers;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<User> subordinates) {
        this.subordinates = subordinates;
    }

    public static String generateRandomPassword(){
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        String password = "";
   
      for(int i = 0; i< 8 ; i++) {
         password += combinedChars.charAt(random.nextInt(combinedChars.length()));
      }
      
        System.out.println(password);      
      return password;
    }

}
