package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.dao.InterviewDAO;
import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    public InterviewServiceImpl(InterviewDAO interviewDAO) {
        this.interviewDAO = interviewDAO;
    }

    private InterviewDAO interviewDAO;

    @Override
    public ResponseEntity<Interview> getInterviewById(Integer id) {
        return interviewDAO.getInterviewById(id);
    }

    @Override
    public ResponseEntity<Set<Interview>> getInterviewsForRecruiter(User user) {
        return this.interviewDAO.getInterviewsForRecruiter(user);
    }

    @Override
    public ResponseEntity<Set<Interview>> getInterviewsForInterviewer(User user) {
        return this.interviewDAO.getInterviewsForInterviewer(user);
    }

    @Override
    public ResponseEntity<Interview> save(Interview interview, User user) {
        return this.interviewDAO.save(interview, user);
    }

    @Override
    public ResponseEntity<Interview> approveSchedule(Integer id, User user) {
        return this.interviewDAO.approveSchedule(id, user);
    }

    @Override
    public ResponseEntity<Interview> rescheduleInterview(Interview interview, User user) {
        return interviewDAO.rescheduleInterview(interview, user);
    }


    @Override
    public ResponseEntity<Interview> updateFeedback(Interview interview, User user) {
        return interviewDAO.updateFeedback(interview, user);
    }

}
