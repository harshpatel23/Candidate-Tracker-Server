package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.CandidateService;
import com.example.candidatetracker.demo.service.JwtUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
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
        
        try{
            Object principal = authentication.getPrincipal();

            User user = null;
            if (principal instanceof JwtUserDetails) {
                user = ((JwtUserDetails) principal).getUser();
            }
            return this.candidateService.getAll(user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}/cv")
    public ResponseEntity<Resource> getCV(@PathVariable String id) {

        try{
            return this.candidateService.getCV(Integer.parseInt(id));
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Candidate> saveCandidate(@RequestBody Candidate candidate) {
        
        try{
            return this.candidateService.save(candidate);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{id}/cv")
    public ResponseEntity<Candidate> uploadResume(@PathVariable String id, @RequestParam("cvFile") MultipartFile cvFile) {
        
        try{
            int candidate_id = Integer.parseInt(id);
            return this.candidateService.uploadCV(candidate_id, cvFile);    
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate) {
        
        try{
            return candidateService.update(candidate);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // maps to id if String can be parsed to integer else considers it as email
    @GetMapping("{identifier}")
    public ResponseEntity<Candidate> getUserById(@PathVariable String identifier) {
        
        try{
            try {
                int id = Integer.parseInt(identifier);
                return this.candidateService.getCandidateById(id);
            } catch (NumberFormatException e) {
                return this.candidateService.getCandidateByEmail(identifier);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/hire/{id}")
    public ResponseEntity<Candidate> hireCandidate(@PathVariable Integer id) {
        
        try{
            return candidateService.changeCandidateStatus(id, "hired");
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Candidate> rejectCandidate(@PathVariable Integer id) {
        
        try{
            return candidateService.changeCandidateStatus(id, "rejected");
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

}

