package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.CandidateService;
import com.example.candidatetracker.demo.service.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<List<Candidate>> getAllCandidates(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        User user = null;
        if (principal instanceof JwtUserDetails) {
            user = ((JwtUserDetails) principal).getUser();
        }
        return this.candidateService.getAll(user);
    }

    @GetMapping("{id}/cv")
    public ResponseEntity<Resource> getCV(@PathVariable String id) {
        return this.candidateService.getCV(Integer.parseInt(id));
    }

    @PostMapping("")
    public ResponseEntity<Candidate> saveCandidate(@RequestBody Candidate candidate) {
        candidateService.save(candidate);
        return this.candidateService.save(candidate);
    }

    @PostMapping("{id}/cv")
    public ResponseEntity<Candidate> uploadResume(@PathVariable String id, @RequestParam("cvFile") MultipartFile cvFile) {
        int candidate_id = Integer.parseInt(id);
        return this.candidateService.uploadCV(candidate_id, cvFile);

    }

    @PutMapping("")
    public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate) {
        return candidateService.update(candidate);
    }

    // maps to id if String can be parsed to integer else considers it as email
    @GetMapping("{identifier}")
    public ResponseEntity<Candidate> getUserById(@PathVariable String identifier) {
        try {
            int id = Integer.parseInt(identifier);
            return this.candidateService.getCandidateById(id);
        } catch (NumberFormatException e) {
            return this.candidateService.getCandidateByEmail(identifier);
        }
    }

    @PutMapping("/hire/{id}")
    public ResponseEntity<Candidate> hireCandidate(@PathVariable Integer id) {
        return candidateService.changeCandidateStatus(id, "hired");
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Candidate> rejectCandidate(@PathVariable Integer id) {
        return candidateService.changeCandidateStatus(id, "rejected");
    }

}

