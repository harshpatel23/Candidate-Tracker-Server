package com.example.candidatetracker.demo.dao;

import java.util.List;

import com.example.candidatetracker.demo.entity.User;

public interface UserDAO {

    public List<User> findAll();

    public User findById(int id);

    public User save(User user);

    public void deleteById(int id);

    public User findByEmail(String email);

}