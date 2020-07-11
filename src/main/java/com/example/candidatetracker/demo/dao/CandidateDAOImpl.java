package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CandidateDAOImpl implements CandidateDAO {

    @Autowired
    public CandidateDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;


    @Override
    public List<Candidate> getAll() {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Candidate");
        List<Candidate> candidateList = query.getResultList();
        return candidateList;
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
