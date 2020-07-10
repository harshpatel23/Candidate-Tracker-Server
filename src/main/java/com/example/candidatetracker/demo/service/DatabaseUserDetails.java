package com.example.candidatetracker.demo.service;

import java.util.Collection;

import com.example.candidatetracker.demo.entity.Role;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DatabaseUserDetails implements UserDetails {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    
    User user;

    public DatabaseUserDetails(User user){
        this.user = user;
    }

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    public int getId(){
        return this.user.getId();
    }

    public Role getRole(){
        return this.user.getRole();
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    
}