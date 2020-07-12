package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;

import java.util.Set;

public interface CandidateService {

    public Set<Candidate> getAll(User currentUser);

    public Candidate save(Candidate candidate);

    public Candidate update(Candidate candidate);

    public Candidate getCandidateById(int id);

    public Candidate getCandidateByEmail(String email);
}
