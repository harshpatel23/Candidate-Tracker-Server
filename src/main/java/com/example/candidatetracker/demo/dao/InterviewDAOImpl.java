package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

@Repository
public class InterviewDAOImpl implements InterviewDAO {

    @Autowired
    public InterviewDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    @Override
    public Interview getInterviewById(Integer id) {
        Session session = entityManager.unwrap(Session.class);
        Interview interview = session.get(Interview.class, id);
        return interview;
    }

    @Override
    public Set<Interview> getInterviewsForRecruiter(User user) {
        Session session = entityManager.unwrap(Session.class);
        User currUser = session.get(User.class, user.getId());
        Set<Interview> interviewSet = new HashSet<>();
        for (Candidate c : currUser.getCandidates())
            interviewSet.addAll(c.getInterviews());
        return interviewSet;
    }

    @Override
    public Set<Interview> getInterviewsForInterviewer(User user) {
        Session session = entityManager.unwrap(Session.class);
        User currUser = session.get(User.class, user.getId());
        return currUser.getInterviews();
    }

    @Override
    public Interview save(Interview interview, User user) {
        Session session = entityManager.unwrap(Session.class);
        interview.setUpdatedBy(user);
        interview.setApprovalStatus("recruiter_approved");
        interview.setComplete(false);
        session.save(interview);
        return interview;
    }
}
