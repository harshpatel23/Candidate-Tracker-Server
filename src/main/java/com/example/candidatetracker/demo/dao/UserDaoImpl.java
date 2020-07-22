package com.example.candidatetracker.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.example.candidatetracker.demo.entity.PasswordData;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.EmailService;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDAO{

    private EntityManager entityManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailService emailService;

    @Autowired
    public UserDaoImpl(EntityManager entityManager, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService){
        this.entityManager = entityManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }


    @Override
    public ResponseEntity<List<User>> findAllSuccessors(User current_user) throws Exception {

        int userId = current_user.getId();
        Session session = entityManager.unwrap(Session.class);
        
        Query<User> query = session.createQuery("select u from User u where u.id = :userId", User.class).setParameter("userId",userId);

        User user = query.getSingleResult();

        List<User> successors = user.getSuccessors().stream().sorted((u1, u2) -> Integer.valueOf(u1.getId()).compareTo(Integer.valueOf(u2.getId()))).collect(Collectors.toList());

        return new ResponseEntity<>(successors, HttpStatus.OK);    
    }

    @Override
	public ResponseEntity<User> findById(int id) throws Exception {

        Session session = entityManager.unwrap(Session.class);

        User user = session.get(User.class, id);

        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
	}

    @Override
	public ResponseEntity<User> findByEmail(String email) throws Exception {
        
        Session session = entityManager.unwrap(Session.class);
        
        Query<User> query = session.createQuery("select u from User u where u.email = :email", User.class).setParameter("email", email);

        User user; 
        try{
            user = query.getSingleResult();
        }catch(NoResultException e){
            user = null;
        }

        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(user, HttpStatus.NOT_FOUND);

	}

    @Override
    public ResponseEntity<List<User>> findByRole(String role, User current_user) throws Exception {

        int userId = current_user.getId();
            
        Session session = entityManager.unwrap(Session.class);
        
        Query<User> query = session.createQuery("select u from User u where u.id = :userId", User.class).setParameter("userId",userId);

        User user = query.getSingleResult();
        
        List<User> userList = user.getSuccessors().stream().filter(u -> u.getRole().getRole().equals(role)).collect(Collectors.toList());

        return new ResponseEntity<>(userList, HttpStatus.OK);

    }
    
    @Override
	public ResponseEntity<User> save(User user) throws Exception {

        System.out.println(user);

        Session session = entityManager.unwrap(Session.class);

        //check if user email already exists
        User userWithSameEmail = findByEmail(user.getEmail()).getBody();
        if(userWithSameEmail != null){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        String password = User.generateRandomPassword();
        
        //Send mail to user about this password
        try{
            emailService.sendPassword(user.getEmail(), password);
        }catch(Exception e){
            System.out.println("Some error occured");
            return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(password); 
        user.setPassword(encryptedPassword);
        user.setIsActive(1);
        session.save(user);
        
        return new ResponseEntity<>(user, HttpStatus.OK);
	}

    @Override
    @Transactional
    public ResponseEntity<User> update(User user) throws Exception {
        Session session = entityManager.unwrap(Session.class);

        User existing_user = session.find(User.class, user.getId());

        if(existing_user == null){
            return new ResponseEntity<>(existing_user, HttpStatus.NOT_FOUND);
        }

        //User cannot change password through this endpoint...hence copy original password from database.
        // user.setPassword(existing_user.getPassword());

        // User updated_user = (User)session.merge(user);

        existing_user.setFirstName(user.getFirstName());
        existing_user.setLastName(user.getLastName());
        existing_user.setContact(user.getContact());
        existing_user.setIsActive(user.getIsActive());
        
        session.close();

        return new ResponseEntity<>(existing_user,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updatePassword(PasswordData passwordData, User user) throws Exception {

        Session session = entityManager.unwrap(Session.class);

        User existingUser = session.find(User.class, user.getId());

        String existingPassword = existingUser.getPassword();


        if(bCryptPasswordEncoder.matches(passwordData.getOldPassword(), existingPassword)){
            existingUser.setPassword(bCryptPasswordEncoder.encode(passwordData.getNewPassword()));
            session.save(existingUser);
            return new ResponseEntity<>("Password Changed Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Old Password incorrect", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<User>> getInterviewers(User user)  throws Exception{
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("select u from User u where u.role = 'interviewer'");

        if(user.getRole().getRole().equals("recruiter")){
            return new ResponseEntity<>(query.list(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    
}