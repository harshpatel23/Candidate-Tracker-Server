package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.entity.Candidate;

import java.util.List;

public interface CandidateService {

    public List<Candidate> getAll();

    public Candidate save(Candidate candidate);

    public Candidate update(Candidate candidate);
}
