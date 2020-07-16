package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public class CandidateDAOImpl implements CandidateDAO {

    @Autowired
    public CandidateDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    @Override
    public ResponseEntity<List<Candidate>> getAll(User currentUser) {
        int userId = currentUser.getId();
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);

        Set<User> successors = user.getSuccessors();

        Query query = session.createQuery("select c from Candidate c where c.recruiter in :successor", Candidate.class).setParameter("successor", successors);

        List<Candidate> candidates = query.list();
        if (candidates == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
    }

    @Transactional
    @Override
    public Candidate save(Candidate candidate) {
        Session session = entityManager.unwrap(Session.class);
        candidate.setCurrentRound(0);
        candidate.setStatus("ready");
        candidate.setLastUpdated(new Date());

        session.save(candidate);
        return candidate;
    }

    @Transactional
    @Override
    public Candidate update(Candidate candidate) {
        Session session = entityManager.unwrap(Session.class);
        candidate.setCurrentRound(0);
        candidate.setStatus("ready");
        candidate.setLastUpdated(new Date());

        session.saveOrUpdate(candidate);
        return candidate;
    }


    // Any signed in user can acees any candidate as of now
    @Override
    public ResponseEntity<Candidate> getCandidateById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Candidate candidate = session.get(Candidate.class, id);
        if (candidate != null)
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Any signed in user can acees any candidate as of now
    @Override
    public ResponseEntity<Candidate> getCandidateByEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        Query<Candidate> query = session.createQuery("select c from Candidate c where c.email = :email", Candidate.class).setParameter("email", email);
        Candidate candidate;
        try {
            candidate = query.getSingleResult();
        } catch (NoResultException e) {
            candidate = null;
        }
        if (candidate != null)
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Candidate> changeCandidateStatus(Integer id, String status) {
        if (id == null || status == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Session session = entityManager.unwrap(Session.class);
        Candidate toBeModified = session.find(Candidate.class, id);
        if (toBeModified == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        toBeModified.setStatus(status);
        Date date = new Date();
        Date d = new Date(date.getTime());
        toBeModified.setLastUpdated(d);
        session.saveOrUpdate(toBeModified);
        return new ResponseEntity<>(toBeModified, HttpStatus.OK);
    }

}
