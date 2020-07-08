package com.example.candidatetracker.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.example.candidatetracker.demo.entity.User;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /* ---------------------------------------------------------

    ToDo : Fetch id from current logged in user instead of hardcoding

    ----------------------------------------------------------*/

    int userId = 2;

    @Override
    public List<User> findAll() {
        
        Session session = entityManager.unwrap(Session.class);
        
        Query<User> query = session.createQuery("select u from User u where u.id = :userId", User.class).setParameter("userId",userId);

        User user = query.getSingleResult();
        
        return new ArrayList<User>(user.getSuccessors());    
    }

    @Override
	public User findById(int id) {

        Session session = entityManager.unwrap(Session.class);

        User user = session.get(User.class, id);

        return user;
	}

	@Override
    public void deleteById(int id) {

        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("delete from User where id = :id").setParameter("id", id);
   
        query.executeUpdate();
	}

    @Override
	public User findByEmail(String email) {
        
        Session session = entityManager.unwrap(Session.class);
        
        Query<User> query = session.createQuery("select u from User u where u.email = :email", User.class).setParameter("email", email);

        User user; 
        try{
            user = query.getSingleResult();
        }catch(NoResultException e){
            user = null;
        }

        return user;
	}

    @Override
    public List<User> findByRole(String role) {
        
        Session session = entityManager.unwrap(Session.class);
        
        Query<User> query = session.createQuery("select u from User u where u.id = :userId", User.class).setParameter("userId",userId);

        User user = query.getSingleResult();
        
        return user.getSuccessors().stream().filter(u -> u.getRole().getRole().equals(role)).collect(Collectors.toList());

    }
    
    @Override
	public User save(User user) {

        Session session = entityManager.unwrap(Session.class);
        user.setPassword(User.generateRandomPassword());
        user.setIsActive(1);
        session.save(user);
        
        return user;
	}

    @Override
    @Transactional
    public User update(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.update(user);
        return user;
    }
    
}