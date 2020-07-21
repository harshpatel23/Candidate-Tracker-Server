package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.InterviewService;
import com.example.candidatetracker.demo.service.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    private InterviewService interviewService;

    @Autowired
    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // find Interview by email or id
    @GetMapping("{id}")
    public ResponseEntity<Interview> getUserById(@PathVariable Integer id) {
        
        try{
            return this.interviewService.getInterviewById(id);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Set<Interview>> getAllInterviews(Authentication authentication) {
        
        try{
            Object principal = authentication.getPrincipal();
            User user = null;
            if (principal instanceof JwtUserDetails)
                user = ((JwtUserDetails) principal).getUser();                
            if (user.getRole().getRole().equals("recruiter")) {
                return this.interviewService.getInterviewsForRecruiter(user);
            } else if (user.getRole().getRole().equals("interviewer")){
                return this.interviewService.getInterviewsForInterviewer(user);
            } else{
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("")
    public ResponseEntity<Interview> saveInterview(@RequestBody Interview interview, Authentication authentication) {
        
        try{
            Object principal = authentication.getPrincipal();
            User user = null;
            if (principal instanceof JwtUserDetails) {
                user = ((JwtUserDetails) principal).getUser();
            }
            return this.interviewService.save(interview, user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Interview> approveSchedule(@PathVariable Integer id, Authentication authentication) {
        
        try{
            Object principal = authentication.getPrincipal();
            User user = null;
            if (principal instanceof JwtUserDetails) {
                user = ((JwtUserDetails) principal).getUser();
            }
            return this.interviewService.approveSchedule(id, user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/reschedule")
    public ResponseEntity<Interview> rescheduleInterview(@RequestBody Interview interview, Authentication authentication) {
        
        try{
            Object principal = authentication.getPrincipal();
            User user = null;
            if (principal instanceof JwtUserDetails) {
                user = ((JwtUserDetails) principal).getUser();
            }
            return this.interviewService.rescheduleInterview(interview, user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }

    @PutMapping("/feedback")
    public ResponseEntity<Interview> updateFeedback(@RequestBody Interview interview, Authentication authentication) {
        
        try{
            Object principal = authentication.getPrincipal();
            User user = null;
            if (principal instanceof JwtUserDetails) {
                user = ((JwtUserDetails) principal).getUser();
            }
            return this.interviewService.updateFeedback(interview, user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
