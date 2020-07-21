package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.PasswordData;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.JwtUserDetails;
import com.example.candidatetracker.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> findAllSuccessors(Authentication authentication) {
        
        try{
            Object principal = authentication.getPrincipal();

            User user = null;
            if(principal instanceof JwtUserDetails){
                user = ((JwtUserDetails)principal).getUser();
            }
            return this.userService.findAllSuccessors(user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/interviewers")
    public ResponseEntity<List<User>> getInterviewers(Authentication authentication){
        
        try{
            Object principal = authentication.getPrincipal();

            User user = null;
            if(principal instanceof JwtUserDetails){
                user = ((JwtUserDetails)principal).getUser();
            }
            return this.userService.getInterviewers(user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("{identifier}")             //find by id / email
    public ResponseEntity<User> getUserById(@PathVariable String identifier){
        
        try{
            try{
                int id = Integer.parseInt(identifier);
                return this.userService.findById(id);
            }catch(NumberFormatException e){
                return this.userService.findByEmail(identifier);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> findSuccessorsByRole(@PathVariable String role, Authentication authentication){
        
        try{
            Object principal = authentication.getPrincipal();

            User user = null;
            if(principal instanceof JwtUserDetails){
                user = ((JwtUserDetails)principal).getUser();
            }
    
            return this.userService.findByRole(role,user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        
        try{
            return this.userService.save(user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        
        try{
            return this.userService.update(user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/password")
    public ResponseEntity<Object> updatePassword(@RequestBody PasswordData passwordData, Authentication authentication){
        
        try{
            Object principal = authentication.getPrincipal();

            User user = null;
            if(principal instanceof JwtUserDetails){
                user = ((JwtUserDetails)principal).getUser();
            }
    
            return this.userService.updatePassword(passwordData, user);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}