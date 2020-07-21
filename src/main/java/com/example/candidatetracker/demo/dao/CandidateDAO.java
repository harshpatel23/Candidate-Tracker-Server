package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateDAO {

    public ResponseEntity<List<Candidate>> getAll(User currentUser) throws Exception;

    public ResponseEntity<Candidate> save(Candidate candidate) throws Exception;

    public ResponseEntity<Candidate> update(Candidate candidate) throws Exception;

    public ResponseEntity<Candidate> getCandidateById(int Id) throws Exception;

    public ResponseEntity<Candidate> getCandidateByEmail(String email) throws Exception;

    public ResponseEntity<Candidate> changeCandidateStatus(Integer id, String status) throws Exception;

	public ResponseEntity<Candidate> uploadCV(int candidate_id, MultipartFile cvFile) throws Exception;

	public ResponseEntity<Resource> getCV(int id) throws Exception;
}
