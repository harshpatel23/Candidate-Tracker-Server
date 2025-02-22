package com.example.candidatetracker.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.candidatetracker.demo.entity.Role;
import com.example.candidatetracker.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    User user;

    public JwtUserDetails(User user){
        this.user = user;
    }

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(this.user.getRole().getRole()));
        return authList;
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
        return this.user.getIsActive() == 1 ? true : false;
    }
    
}