package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    private CandidateService candidateService;

    @GetMapping("")
    public List<Candidate> getAllCandidates() {
        return candidateService.getAll();
    }

    @PostMapping("")
    public Candidate saveCandidate(@RequestBody Candidate candidate){
        candidateService.save(candidate);
        return candidate;
    }

    @PutMapping("")
    public Candidate updateCandidate(@RequestBody Candidate candidate){
        candidateService.update(candidate);
        return candidate;
    }



}

