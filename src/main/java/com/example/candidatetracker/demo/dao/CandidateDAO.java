package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;

import java.util.Set;

public interface CandidateDAO {

    public Set<Candidate> getAll(User currentUser);

    public Candidate save(Candidate candidate);

    public Candidate update(Candidate candidate);

    public Candidate getCandidateById(int Id);

    public Candidate getCandidateByEmail(String email);

    public Candidate changeCandidateStatus(Integer id, String status);
}
