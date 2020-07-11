package com.example.candidatetracker.demo.service;

import java.util.List;

import com.example.candidatetracker.demo.entity.PasswordData;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.http.ResponseEntity;

public interface UserService {

    public List<User> findAllSuccessors(User user);
    
    public ResponseEntity<User> findById(int id);

    public ResponseEntity<User> save(User user);

    public ResponseEntity<User> findByEmail(String email);

	public ResponseEntity<List<User>> findByRole(String role, User user);

	public ResponseEntity<User> update(User user);

	public ResponseEntity<Object> updatePassword(PasswordData passwordData, User user);

}