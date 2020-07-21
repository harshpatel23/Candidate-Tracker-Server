package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.Interview;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.CalendarService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class InterviewDAOImpl implements InterviewDAO {

    private EntityManager entityManager;

    private CalendarService calendarService;

    @Autowired
    public InterviewDAOImpl(EntityManager entityManager, CalendarService calendarService) {
        this.entityManager = entityManager;
        this.calendarService = calendarService;
    }

    @Override
    public ResponseEntity<Interview> getInterviewById(Integer id) throws Exception{
        Session session = entityManager.unwrap(Session.class);
        Interview interview = session.get(Interview.class, id);
        return interview != null ? new ResponseEntity<>(interview, HttpStatus.OK) : new ResponseEntity<>(interview, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Set<Interview>> getInterviewsForRecruiter(User user) throws Exception {
        Session session = entityManager.unwrap(Session.class);
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        User currUser = session.get(User.class, user.getId());
        Set<Interview> interviewSet = new HashSet<>();
        for (Candidate c : currUser.getCandidates()) interviewSet.addAll(c.getInterviews().stream().filter(interview -> !(interview.isComplete())).collect(Collectors.toList()));
        return new ResponseEntity<>(interviewSet, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<Interview>> getInterviewsForInterviewer(User user) throws Exception {
        Session session = entityManager.unwrap(Session.class);
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        User currUser = session.get(User.class, user.getId());

        Set<Interview> interviews = currUser.getInterviews().stream().filter(interview -> !(interview.isComplete())).collect(Collectors.toSet());
        return new ResponseEntity<>(interviews, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Interview> save(Interview interview, User user) throws Exception {
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        else if (user.getRole().getRole().equals("interviewer")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Session session = entityManager.unwrap(Session.class);
        interview.setUpdatedBy(user);
        interview.setApprovalStatus("recruiter_approved");
        interview.setComplete(false);

        Candidate candidate = session.get(Candidate.class, interview.getCandidate().getId());
        interview.setRoundNum(candidate.getCurrentRound() + 1);

        session.save(interview);
        return new ResponseEntity<>(interview, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Interview> approveSchedule(Integer id, User user) throws Exception {
        Session session = entityManager.unwrap(Session.class);
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        Interview interview = session.get(Interview.class, id);

        //Creating Calender event;
        Date startDate = interview.getStartTime();
        Date endDate = interview.getEndTime();
        String interviewer_email = session.get(User.class, interview.getInterviewer().getId()).getEmail();
        String candidate_email = session.get(Candidate.class, interview.getCandidate().getId()).getEmail();
        try {
            calendarService.createEvent(startDate, endDate, interviewer_email, candidate_email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        interview.setApprovalStatus("both_approved");
        interview.setUpdatedBy(user);
        interview.getCandidate().setStatus("hold");
        session.saveOrUpdate(interview);
        return new ResponseEntity<>(interview, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Interview> rescheduleInterview(Interview interview, User user) throws Exception {
        Session session = entityManager.unwrap(Session.class);
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
        return new ResponseEntity<>(interview, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Interview> updateFeedback(Interview interview, User user) {
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
        return new ResponseEntity<>(curr, HttpStatus.OK);
    }


}
