package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CandidateService {

    public ResponseEntity<List<Candidate>> getAll(User currentUser);

    public Candidate save(Candidate candidate);

    public Candidate update(Candidate candidate);

    public ResponseEntity<Candidate> getCandidateById(int id);

    public ResponseEntity<Candidate> getCandidateByEmail(String email);

    public ResponseEntity<Candidate> changeCandidateStatus(Integer id, String status);
}
