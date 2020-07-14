package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.HashSet;
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
    public Set<Candidate> getAll(User currentUser) {
        int userId = currentUser.getId();
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);

        Set<User> successors = user.getSuccessors();

        Query query = session.createQuery("select c from Candidate c where c.recruiter in :successor", Candidate.class).setParameter("successor", successors);

        List<Candidate> candidates = query.list();

        return new HashSet<Candidate>(candidates);
    }

    @Override
    public Candidate save(Candidate candidate) {
        Session session = entityManager.unwrap(Session.class);
        candidate.setCurrentRound(0);
        candidate.setStatus("ready");
        session.save(candidate);
        return candidate;
    }

    @Transactional
    @Override
    public Candidate update(Candidate candidate) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(candidate);
        return candidate;
    }

    @Override
    public Candidate getCandidateById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Candidate candidate = session.get(Candidate.class, id);
        return candidate;
    }

    @Override
    public Candidate getCandidateByEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        Query<Candidate> query = session.createQuery("select c from Candidate c where c.email = :email", Candidate.class).setParameter("email", email);
        Candidate candidate;
        try {
            candidate = query.getSingleResult();
        } catch (NoResultException e) {
            candidate = null;
        }
        return candidate;
    }

    @Override
    public Candidate changeCandidateStatus(Integer id, String status) {
        Session session = entityManager.unwrap(Session.class);
        Candidate toBeModified = session.find(Candidate.class, id);
        toBeModified.setStatus(status);
        Date date = new Date();
        Date d = new Date(date.getTime() + 330 * 60 * 1000);
        toBeModified.setLastUpdated(d);
        session.saveOrUpdate(toBeModified);
        return toBeModified;
    }

}
