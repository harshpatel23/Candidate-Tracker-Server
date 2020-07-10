package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.dao.RoleDAO;
import com.example.candidatetracker.demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Transactional
    @Override
    public List<Role> findAll() {
        return roleDAO.findAll();
    }
}
