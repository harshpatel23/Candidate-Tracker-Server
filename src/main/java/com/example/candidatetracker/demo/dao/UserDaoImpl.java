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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDAO {

    private EntityManager entityManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserDaoImpl(EntityManager entityManager, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.entityManager = entityManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
    public void disableById(int id) {

        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("update User u set u.isActive = 0 where u.id = :id").setParameter("id", id);
   
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

        String password = User.generateRandomPassword();
        //Send mail to user about this password
        String encryptedPassword = bCryptPasswordEncoder.encode(password); 
        user.setPassword(encryptedPassword);
        user.setIsActive(1);
        session.save(user);
        
        return user;
	}

    @Override
    @Transactional
    public User update(User user) {
        Session session = entityManager.unwrap(Session.class);

        //Check if password has changed or not...if new password not available then copy existing password.
        User existing_user = session.find(User.class, user.getId());
        user.setPassword(existing_user.getPassword());
        //User can't be disabled through this method, use delete request instead.
        user.setIsActive(existing_user.getIsActive());
        session.merge(user);

        return user;
    }
    
}