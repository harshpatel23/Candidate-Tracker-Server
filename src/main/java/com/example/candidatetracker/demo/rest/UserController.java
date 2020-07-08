package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController{

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> findAllUsers() {
        return this.userService.findAll();
    }

    @GetMapping("{identifier}")             //find by id / email
    public User getUserById(@PathVariable String identifier){
        try{
            int id = Integer.parseInt(identifier);
            return this.userService.findById(id);
        }catch(NumberFormatException e){
            return this.userService.findByEmail(identifier);
        }
    }

    @GetMapping("/role/{role}")
    public List<User> findSuccessorsByRole(@PathVariable String role){
        return this.userService.findByRole(role);
    }

    @PostMapping("")
    public User saveUser(@RequestBody User user) {
        return this.userService.save(user);
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) {
        return this.userService.save(user);
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable int id){

        User user = userService.findById(id);
        if(user == null){
            return "User does not exist";
        }

        this.userService.deleteById(id);
        return "User deleted";
    }

}