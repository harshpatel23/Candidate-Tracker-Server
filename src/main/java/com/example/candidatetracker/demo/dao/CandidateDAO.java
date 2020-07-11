package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;

import java.util.List;

public interface CandidateDAO {

    public List<Candidate> getAll();

    public Candidate save(Candidate candidate);

    public Candidate update(Candidate candidate);

}
