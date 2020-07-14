package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;

import java.util.Set;

public interface InterviewDAO {

    public Interview getInterviewById(Integer id);

    Set<Interview> getInterviewsForRecruiter(User user);

    Set<Interview> getInterviewsForInterviewer(User user);

    Interview save(Interview interview, User user);
}
