package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.CalendarService;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Repository
public class InterviewDAOImpl implements InterviewDAO {

    private EntityManager entityManager;
    private CalendarService calendarService;

    @Autowired
    public InterviewDAOImpl(EntityManager entityManager, CalendarService calendarService){
        this.entityManager = entityManager;
        this.calendarService = calendarService;
    }

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

        //Creating Calender event;
        Date startDate = interview.getStartTime();
        Date endDate = interview.getEndTime();

        String interviewer_email = session.get(User.class, interview.getInterviewer().getId()).getEmail();
        String candidate_email = session.get(Candidate.class, interview.getCandidate().getId()).getEmail();
        
        try{
            calendarService.createEvent(startDate, endDate, interviewer_email, candidate_email);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

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
        curr.setEndTime(interview.getEndTime());
        curr.setComplete(false);
        if (user.getRole().getRole().equals("recruiter") && curr.getApprovalStatus().equals("interviewer_approved"))
            curr.setApprovalStatus("recruiter_approved");
        else if (user.getRole().getRole().equals("interviewer") && curr.getApprovalStatus().equals("recruiter_approved"))
            curr.setApprovalStatus("interviewer_approved");
        session.saveOrUpdate(curr);
        return curr;
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
