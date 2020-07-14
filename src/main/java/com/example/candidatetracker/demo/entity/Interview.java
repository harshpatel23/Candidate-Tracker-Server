package com.example.candidatetracker.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int interviewId;

    @ManyToOne
    @JsonIgnoreProperties({"interviews", "skillSet"})
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @JsonIgnoreProperties({"managers", "successors", "subordinates", "manager"})
    @ManyToOne
    @JoinColumn(name = "interviewer_id")
    private User interviewer;

    @JoinColumn(name = "approval_status")
    private String approvalStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale="en_IN", timezone = "Asia/Kolkata")
    @Column(name = "start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale="en_IN", timezone = "Asia/Kolkata")
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "round_no")
    private int roundNum;

    @Column(columnDefinition = "TINYINT", name = "is_complete")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean complete;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    public Interview() {
    }

    public Interview(Date startTime, Date endTime, String feedback, int roundNum) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.feedback = feedback;
        this.roundNum = roundNum;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getInterviewId() {
        return interviewId;
    }
}
