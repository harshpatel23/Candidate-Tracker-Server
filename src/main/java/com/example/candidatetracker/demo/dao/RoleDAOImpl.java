package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private EntityManager entityManager;

    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Role> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Role", Role.class);
        List<Role> roleList =  query.getResultList();
        return roleList;
    }
}
