package com.example.candidatetracker.demo.service;

import java.util.List;

import com.example.candidatetracker.demo.dao.UserDAO;
import com.example.candidatetracker.demo.entity.PasswordData;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiveImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiveImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    @Override
    @Transactional
    public List<User> findAllSuccessors(User user) {
        return userDAO.findAllSuccessors(user);
    }

    @Override
    @Transactional
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    @Transactional
    public void disableById(int id) {
        userDAO.disableById(id);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    @Transactional
    public List<User> findByRole(String role, User user) {
        return this.userDAO.findByRole(role, user);
    }

    @Override
    @Transactional
    public User update(User user) {
        return this.userDAO.update(user);        
    }

    @Override
    @Transactional
    public String updatePassword(PasswordData passwordData, User user) {
        return this.userDAO.updatePassword(passwordData, user);
    }

    
}