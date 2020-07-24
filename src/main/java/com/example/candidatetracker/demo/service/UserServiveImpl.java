package com.example.candidatetracker.demo.service;

import java.util.List;
import java.util.Set;

import com.example.candidatetracker.demo.dao.UserDAO;
import com.example.candidatetracker.demo.entity.PasswordData;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiveImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiveImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public ResponseEntity<List<User>> findAllSuccessors(User user) throws Exception {
        return userDAO.findAllSuccessors(user);
    }

    @Override
    @Transactional
    public ResponseEntity<User> findById(int id) throws Exception {
        return userDAO.findById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<User> save(User user) throws Exception {
        return userDAO.save(user);
    }

    @Override
    @Transactional
    public ResponseEntity<User> findByEmail(String email) throws Exception {
        return userDAO.findByEmail(email);
    }

    @Override
    @Transactional
    public ResponseEntity<List<User>> findByRole(String role, User user) throws Exception {
        return this.userDAO.findByRole(role, user);
    }

    @Override
    @Transactional
    public ResponseEntity<User> update(User user) throws Exception {
        return this.userDAO.update(user);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> updatePassword(PasswordData passwordData, User user) throws Exception {
        return this.userDAO.updatePassword(passwordData, user);
    }

    @Override
    public ResponseEntity<List<User>> getInterviewers(User user) throws Exception {
        return this.userDAO.getInterviewers(user);
    }

    @Override
    public ResponseEntity<Set<User>> getAllManagers(int id) throws Exception{
        return this.userDAO.getAllManagers(id);
    }

    
}