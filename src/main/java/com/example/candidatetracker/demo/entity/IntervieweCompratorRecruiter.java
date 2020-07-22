package com.example.candidatetracker.demo.entity;

import java.util.Comparator;

public class IntervieweCompratorRecruiter implements Comparator{

    private final static String INTERVIEWER = "interviewer_approved";
    private final static String RECRUITER = "recruiter_approved";
    private final static String BOTH = "both_approved";
    @Override
    public int compare(Object o1, Object o2) {
        Interview i1 = (Interview)o1;
        Interview i2 = (Interview)o2;
        
        if(i1.getApprovalStatus().equals(i2.getApprovalStatus())){
            return i1.getStartTime().getTime() > i2.getStartTime().getTime() ? 1 : -1;
        }else if(i1.getApprovalStatus().equals(INTERVIEWER)){
            return -1;
        }else if(i1.getApprovalStatus().equals(BOTH)){
            return 1;
        }else if(i1.getApprovalStatus().equals(RECRUITER)){
            return i2.getApprovalStatus().equals(INTERVIEWER) ? 1 : -1;
        }

        return 0;

    }
    
}