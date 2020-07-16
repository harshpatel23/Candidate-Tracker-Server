package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.dao.CandidateDAO;
import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    public CandidateServiceImpl(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    private CandidateDAO candidateDAO;

    @Override
    @Transactional
    public ResponseEntity<List<Candidate>> getAll(User currentUser) {
        return candidateDAO.getAll(currentUser);
    }

    @Override
    @Transactional
    public ResponseEntity<Candidate> save(Candidate candidate) {
        return candidateDAO.save(candidate);
    }

    @Override
    @Transactional
    public Candidate update(Candidate candidate) {
        return candidateDAO.update(candidate);
    }

    @Override
    @Transactional
    public ResponseEntity<Candidate> getCandidateById(int id) {
        return candidateDAO.getCandidateById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<Candidate> getCandidateByEmail(String email) {
        return candidateDAO.getCandidateByEmail(email);
    }

    @Override
    @Transactional
    public ResponseEntity<Candidate> changeCandidateStatus(Integer id, String status) {
        return candidateDAO.changeCandidateStatus(id, status);
    }

    @Override
    @Transactional
    public ResponseEntity<Candidate> uploadCV(int candidate_id, MultipartFile cvFile) {
        return this.candidateDAO.uploadCV(candidate_id, cvFile);
    }

    @Override
    public ResponseEntity<Resource> getCV(int id) {
        return this.candidateDAO.getCV(id);
    }

}
