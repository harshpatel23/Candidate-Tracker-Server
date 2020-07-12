package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Set;

@Repository
public class CandidateDAOImpl implements CandidateDAO {

    @Autowired
    public CandidateDAOImpl(EntityManager entityManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.entityManager = entityManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private EntityManager entityManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Set<Candidate> getAll(User currentUser) {
        int userId = currentUser.getId();
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);
        return user.getCandidates();
    }

    @Override
    public Candidate save(Candidate candidate) {
        Session session = entityManager.unwrap(Session.class);
        session.save(candidate);
        return candidate;
    }

    @Override
    public Candidate update(Candidate candidate) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(candidate);
        return candidate;
    }


}
