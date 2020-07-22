package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface InterviewService {

    public ResponseEntity<Interview> getInterviewById(Integer id) throws Exception;

    ResponseEntity<List<Interview>> getInterviewsForRecruiter(User user) throws Exception;

    ResponseEntity<List<Interview>> getInterviewsForInterviewer(User user) throws Exception;

    ResponseEntity<Interview> save(Interview interview, User user) throws Exception;

    ResponseEntity<Interview> approveSchedule(Integer id, User user) throws Exception;

    ResponseEntity<Interview> rescheduleInterview(Interview interview, User user) throws Exception;

    ResponseEntity<Interview> updateFeedback(Interview id, User user) throws Exception;
}
