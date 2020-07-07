package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.dao.UserDAO;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController{

    private UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @GetMapping("")
    public List<User> findAllUsers() {
        return this.userDAO.findAll();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable int id){
        return this.userDAO.findById(id);
    }

    // @GetMapping("/email/{email}")
    // public User getUserByEmail(@PathVariable String email){
    //     return this.userDAO.findByEmail(email);
    // }

    @PostMapping("")
    public User saveUser(@RequestBody User user) {
        return this.userDAO.save(user);
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) {
        return this.userDAO.save(user);
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable int id){

        User user = userDAO.findById(id);
        if(user == null){
            return "User does not exist";
        }

        this.userDAO.deleteById(id);
        return "User deleted";
    }

}