package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;

import java.util.Set;

public interface InterviewService {

    public Interview getInterviewById(Integer id);

    Set<Interview> getInterviewsForRecruiter(User user);

    Set<Interview> getInterviewsForInterviewer(User user);

    Interview save(Interview interview, User user);

    Interview approveSchedule(Integer id, User user);

    Interview rescheduleInterview(Interview interview, User user);
}
