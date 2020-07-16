package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {

    public ResponseEntity<List<Candidate>> getAll(User currentUser);

    public ResponseEntity<Candidate> save(Candidate candidate);

    public Candidate update(Candidate candidate);

    public ResponseEntity<Candidate> getCandidateById(int id);

    public ResponseEntity<Candidate> getCandidateByEmail(String email);

    public ResponseEntity<Candidate> changeCandidateStatus(Integer id, String status);

	public ResponseEntity<Candidate> uploadCV(int candidate_id, MultipartFile cvFile);

	public ResponseEntity<Resource> getCV(int id);
}
