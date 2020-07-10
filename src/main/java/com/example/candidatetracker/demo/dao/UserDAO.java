package com.example.candidatetracker.demo.dao;

import java.util.List;

import com.example.candidatetracker.demo.entity.User;

public interface UserDAO {

    public List<User> findAllSuccessors(User currentUser);

    public User findById(int id);

    public User save(User user);

    public void disableById(int id);

    public User findByEmail(String email);

    public List<User> findByRole(String role, User currentUser);

	public User update(User user);

}