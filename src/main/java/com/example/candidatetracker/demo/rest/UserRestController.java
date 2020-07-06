package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.dao.UserDAO;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController{

    private UserDAO userDAO;

    @Autowired
    public UserRestController(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return this.userDAO.findAll();
    }

}