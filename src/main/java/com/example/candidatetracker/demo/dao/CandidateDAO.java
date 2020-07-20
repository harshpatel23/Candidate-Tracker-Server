package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CandidateDAO {

    public ResponseEntity<List<Candidate>> getAll(User currentUser);

    public ResponseEntity<Candidate> save(Candidate candidate);

    public ResponseEntity<Candidate> update(Candidate candidate);

    public ResponseEntity<Candidate> getCandidateById(int Id);

    public ResponseEntity<Candidate> getCandidateByEmail(String email);

    public ResponseEntity<Candidate> changeCandidateStatus(Integer id, String status);
}
