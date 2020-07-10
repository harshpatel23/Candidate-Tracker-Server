package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.PasswordData;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.JwtUserDetails;
import com.example.candidatetracker.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public List<User> findAllSuccessors(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        User user = null;
        if(principal instanceof JwtUserDetails){
            user = ((JwtUserDetails)principal).getUser();
        }
        return this.userService.findAllSuccessors(user);
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
    public List<User> findSuccessorsByRole(@PathVariable String role, Authentication authentication){
        
        Object principal = authentication.getPrincipal();

        User user = null;
        if(principal instanceof JwtUserDetails){
            user = ((JwtUserDetails)principal).getUser();
        }

        return this.userService.findByRole(role,user);
    }

    @PostMapping("")
    public User saveUser(@RequestBody User user) {
        return this.userService.save(user);
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) {
        return this.userService.update(user);
    }

    @PutMapping("/password")
    public String updatePassword(@RequestBody PasswordData passwordData, Authentication authentication){
        Object principal = authentication.getPrincipal();

        User user = null;
        if(principal instanceof JwtUserDetails){
            user = ((JwtUserDetails)principal).getUser();
        }

        return this.userService.updatePassword(passwordData, user);
    }

    @DeleteMapping("{id}")
    public String disableUser(@PathVariable int id){

        User user = userService.findById(id);
        if(user == null){
            return "User does not exist";
        }

        this.userService.disableById(id);
        return "User deleted";
    }

}