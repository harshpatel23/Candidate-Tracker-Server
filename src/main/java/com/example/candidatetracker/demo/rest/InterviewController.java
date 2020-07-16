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
        return this.interviewService.getInterviewById(id);
    }

    @GetMapping("")
    public ResponseEntity<Set<Interview>> getAllInterviews(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof JwtUserDetails)
            user = ((JwtUserDetails) principal).getUser();
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        if (user.getRole().getRole().equals("recruiter")) {
            return this.interviewService.getInterviewsForRecruiter(user);
        } else {
            return this.interviewService.getInterviewsForInterviewer(user);
        }
    }

    @PostMapping("")
    public ResponseEntity<Interview> saveInterview(@RequestBody Interview interview, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof JwtUserDetails) {
            user = ((JwtUserDetails) principal).getUser();
        }
        return this.interviewService.save(interview, user);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Interview> approveSchedule(@PathVariable Integer id, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof JwtUserDetails) {
            user = ((JwtUserDetails) principal).getUser();
        }
        return this.interviewService.approveSchedule(id, user);
    }

    @PutMapping("/reschedule")
    public ResponseEntity<Interview> rescheduleInterview(@RequestBody Interview interview, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof JwtUserDetails) {
            user = ((JwtUserDetails) principal).getUser();
        }
        return this.interviewService.rescheduleInterview(interview, user);
    }

    @PutMapping("/feedback")
    public ResponseEntity<Interview> updateFeedback(@RequestBody Interview interview, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof JwtUserDetails) {
            user = ((JwtUserDetails) principal).getUser();
        }
        return this.interviewService.updateFeedback(interview, user);
    }

}
