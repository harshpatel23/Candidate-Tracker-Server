package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Skill;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SkillDAOImpl implements SkillDAO {

    @Autowired
    public SkillDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    @Override
    public List<Skill> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Skill");
        return query.getResultList();
    }
}
