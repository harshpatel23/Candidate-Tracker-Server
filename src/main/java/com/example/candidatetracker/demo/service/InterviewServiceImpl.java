package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.dao.InterviewDAO;
import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Interview getInterviewById(Integer id) {
        return interviewDAO.getInterviewById(id);
    }

    @Override
    public Set<Interview> getInterviewsForRecruiter(User user) {
        return this.interviewDAO.getInterviewsForRecruiter(user);
    }

    @Override
    public Set<Interview> getInterviewsForInterviewer(User user) {
        return this.interviewDAO.getInterviewsForInterviewer(user);
    }

    @Override
    public Interview save(Interview interview, User user) {
        return this.interviewDAO.save(interview, user);
    }

    @Override
    public Interview approveSchedule(Integer id, User user) {
        return this.interviewDAO.approveSchedule(id, user);
    }

    @Override
    public Interview rescheduleInterview(Interview interview, User user) {
        return interviewDAO.rescheduleInterview(interview, user);
    }


    @Override
    public Interview updateFeedback(Interview interview, User user) {
        return interviewDAO.updateFeedback(interview, user);
    }

}
