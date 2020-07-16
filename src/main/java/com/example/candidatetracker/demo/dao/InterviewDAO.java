package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface InterviewDAO {

    public ResponseEntity<Interview> getInterviewById(Integer id);

    ResponseEntity<Set<Interview>> getInterviewsForRecruiter(User user);

    ResponseEntity<Set<Interview>> getInterviewsForInterviewer(User user);

    ResponseEntity<Interview> save(Interview interview, User user);

    ResponseEntity<Interview> approveSchedule(Integer id, User user);

    ResponseEntity<Interview> rescheduleInterview(Interview interview, User user);

    ResponseEntity<Interview> updateFeedback(Interview interview, User user);
}
