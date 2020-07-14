package com.example.candidatetracker.demo.service;

import org.springframework.stereotype.Service;

public interface EmailService {
    
    void sendEmail(String recipient, String password) throws Exception;

}