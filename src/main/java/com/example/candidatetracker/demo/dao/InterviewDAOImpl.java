package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Interview approveSchedule(Integer id, User user) {
        Session session = entityManager.unwrap(Session.class);
        Interview interview = session.get(Interview.class, id);
        interview.setApprovalStatus("both_approved");
        interview.setUpdatedBy(user);
        interview.getCandidate().setStatus("hold");
        session.saveOrUpdate(interview);
        return interview;
    }

    @Transactional
    @Override
    public Interview rescheduleInterview(Interview interview, User user) {
        Session session = entityManager.unwrap(Session.class);
        Interview curr = session.get(Interview.class, interview.getInterviewId());
        curr.setUpdatedBy(user);
        curr.setStartTime(interview.getStartTime());
        curr.setStartTime(interview.getEndTime());
        curr.setComplete(false);
        if (user.getRole().getRole().equals("recruiter") && curr.getApprovalStatus().equals("interviewer_approved"))
            curr.setApprovalStatus("recruiter_approved");
        else if (user.getRole().getRole().equals("interviewer") && curr.getApprovalStatus().equals("recruiter_approved"))
            curr.setApprovalStatus("interviewer_approved");
        session.saveOrUpdate(curr);
        return interview;
    }

    @Transactional
    @Override
    public Interview updateFeedback(Interview interview, User user) {
        Session session = entityManager.unwrap(Session.class);
        Interview curr = session.get(Interview.class, interview.getInterviewId());
        curr.setUpdatedBy(user);
        curr.setFeedback(interview.getFeedback());
        curr.setComplete(true);
        Candidate candidate = curr.getCandidate();
        if (user.getRole().getRole().equals("interviewer")) {
            candidate.setStatus("ready");
            candidate.setCurrentRound(candidate.getCurrentRound() + 1);
        }
        session.saveOrUpdate(curr);
        return curr;
    }


}
