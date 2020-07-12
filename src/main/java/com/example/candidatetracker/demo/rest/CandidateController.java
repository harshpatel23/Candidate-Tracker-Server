package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.CandidateService;
import com.example.candidatetracker.demo.service.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    private CandidateService candidateService;

    @GetMapping("")
    public Set<Candidate> getAllCandidates(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        User user = null;
        if (principal instanceof JwtUserDetails) {
            user = ((JwtUserDetails) principal).getUser();
        }
        return this.candidateService.getAll(user);
    }

    @PostMapping("")
    public Candidate saveCandidate(@RequestBody Candidate candidate) {
        candidateService.save(candidate);
        return candidate;
    }

    @PutMapping("")
    public Candidate updateCandidate(@RequestBody Candidate candidate) {
        candidateService.update(candidate);
        return candidate;
    }

    // maps to id if String can be parsed to integer else considers it as email
    @GetMapping("{identifier}")
    public Candidate getUserById(@PathVariable String identifier) {
        try {
            int id = Integer.parseInt(identifier);
            return this.candidateService.getCandidateById(id);
        } catch (NumberFormatException e) {
            return this.candidateService.getCandidateByEmail(identifier);
        }
    }
}

