package com.example.candidatetracker.demo.dao;

import java.util.List;

import com.example.candidatetracker.demo.entity.User;

public interface UserDAO {
    public List<User> findAll();
}