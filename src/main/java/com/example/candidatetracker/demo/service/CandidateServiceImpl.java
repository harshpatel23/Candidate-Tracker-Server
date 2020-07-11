package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.dao.CandidateDAO;
import com.example.candidatetracker.demo.entity.Candidate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    public CandidateServiceImpl(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    private CandidateDAO candidateDAO;

    @Override
    public List<Candidate> getAll() {
        return candidateDAO.getAll();
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateDAO.save(candidate);
    }

    @Override
    public Candidate update(Candidate candidate) {
        return candidateDAO.update(candidate);
    }

}
