package com.example.candidatetracker.demo.service;

import java.util.List;

import com.example.candidatetracker.demo.dao.UserDAO;
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
    public List<User> findAll() {
        return userDAO.findAll();
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
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    @Transactional
    public List<User> findByRole(String role) {
        return this.userDAO.findByRole(role);
    }

    
}