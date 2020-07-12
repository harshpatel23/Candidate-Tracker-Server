package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.dao.CandidateDAO;
import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CandidateServiceImpl implements CandidateService {

    public CandidateServiceImpl(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    private CandidateDAO candidateDAO;

    @Override
    public Set<Candidate> getAll(User currentUser) {
        return candidateDAO.getAll(currentUser);
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
