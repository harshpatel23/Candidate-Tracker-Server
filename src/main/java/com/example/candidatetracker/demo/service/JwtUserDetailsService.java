package com.example.candidatetracker.demo.service;

import javax.transaction.Transactional;

import com.example.candidatetracker.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username).getBody();
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        return new JwtUserDetails(user);
    }
    
}