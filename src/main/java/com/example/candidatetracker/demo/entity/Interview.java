package com.example.candidatetracker.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int interviewId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "interviewer_id")
    private User interviewer;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "round_no")
    private int roundNum;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    @JsonIgnore
    //@JsonIgnoreProperties({"managers", "successors", "subordinates", "manager"})
    private User updatedBy;

    public Interview() {
    }

    public Interview(Timestamp startTime, Timestamp endTime, String feedback, int roundNum) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.feedback = feedback;
        this.roundNum = roundNum;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public User getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(User interviewer) {
        this.interviewer = interviewer;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public int getInterviewId() {
        return interviewId;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

}
