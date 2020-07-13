package com.example.candidatetracker.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillId;

    @Column(name = "skill_name")
    private String skillName;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "interviewer_skills",
            joinColumns = {@JoinColumn(name = "skill_id")},
            inverseJoinColumns = {@JoinColumn(name = "interviewer_id")})
    private Set<User> interviewers = new HashSet<>();
    
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "candidate_skills",
            joinColumns = {@JoinColumn(name = "skill_id")},
            inverseJoinColumns = {@JoinColumn(name = "candidate_id")})
    private Set<Candidate> candidates = new HashSet<>();

    public Skill() {
    }

    public Skill(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillId() {
        return skillId;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<User> getInterviewers() {
        return interviewers;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
