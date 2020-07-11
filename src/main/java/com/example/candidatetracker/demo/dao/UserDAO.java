package com.example.candidatetracker.demo.dao;

import java.util.List;

import com.example.candidatetracker.demo.entity.PasswordData;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.http.ResponseEntity;

public interface UserDAO {

    public List<User> findAllSuccessors(User user);

    public User findById(int id);

    public User save(User user);

    public void disableById(int id);

    public User findByEmail(String email);

    public List<User> findByRole(String role, User user);

	public User update(User user);

	public ResponseEntity updatePassword(PasswordData passwordData, User user);

}