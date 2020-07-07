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
        
        Query<User> query = session.createQuery("from User", User.class);

        List<User> users = query.getResultList();
            
        return users;
    
    }

    @Override
    @Transactional
	public User findById(int id) {

        Session session = entityManager.unwrap(Session.class);

        User user = session.get(User.class, id);

        return user;
	}

    @Override
    @Transactional
	public User save(User user) {

        Session session = entityManager.unwrap(Session.class);

        session.saveOrUpdate(user);
        
        return user;
	}

	@Override
    @Transactional
    public void deleteById(int id) {

        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("delete from User where id = :id").setParameter("id", id);
   
        query.executeUpdate();
	}

    @Override
    @Transactional
	public User findByEmail(String email) {
        
        Session session = entityManager.unwrap(Session.class);
        
        Query<User> query = session.createQuery("select u from User u where u.email = :email").setParameter("email", email);

        User user = query.getSingleResult();

        return user;
	}
    
}