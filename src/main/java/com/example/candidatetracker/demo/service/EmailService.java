package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.entity.Candidate;

public interface EmailService {
    
    void sendPassword(String recipient, String password) throws Exception;

    void sendHireRejectMail(String recruiter_email, String status, Candidate candidate) throws Exception;

}