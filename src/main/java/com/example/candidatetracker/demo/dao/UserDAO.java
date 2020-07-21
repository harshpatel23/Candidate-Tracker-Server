package com.example.candidatetracker.demo.dao;

import java.util.List;

import com.example.candidatetracker.demo.entity.PasswordData;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.http.ResponseEntity;

public interface UserDAO {

    public ResponseEntity<List<User>> findAllSuccessors(User user) throws Exception;

    public ResponseEntity<User> findById(int id) throws Exception;

    public ResponseEntity<User> save(User user) throws Exception;

    public ResponseEntity<User> findByEmail(String email) throws Exception;

    public ResponseEntity<List<User>> findByRole(String role, User user) throws Exception;

	public ResponseEntity<User> update(User user) throws Exception;

	public ResponseEntity<Object> updatePassword(PasswordData passwordData, User user) throws Exception;

	public ResponseEntity<List<User>> getInterviewers(User user) throws Exception;

}