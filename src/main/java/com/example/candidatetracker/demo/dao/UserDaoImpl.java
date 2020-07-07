package com.example.candidatetracker.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.example.candidatetracker.demo.entity.User;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        
        Session session = entityManager.unwrap(Session.class);
        
        // Query<User> query = session.createQuery("from User", User.class);

        Query<User> query = session.createQuery("select u from User u where u.id = 2");
        
        User user = query.getSingleResult();

        List<User> users = new ArrayList<User>(user.getSuccessors());

        return users;
    
    }

    
}