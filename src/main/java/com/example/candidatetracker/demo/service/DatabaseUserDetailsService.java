package com.example.candidatetracker.demo.service;

import javax.transaction.Transactional;

import com.example.candidatetracker.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        return new DatabaseUserDetails(user);
    }
    
}