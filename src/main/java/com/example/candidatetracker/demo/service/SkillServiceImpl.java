package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.dao.SkillDAO;
import com.example.candidatetracker.demo.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    public SkillServiceImpl(SkillDAO skillDAO) {
        this.skillDAO = skillDAO;
    }

    private SkillDAO skillDAO;

    @Override
    public List<Skill> findAll() {
        return skillDAO.findAll();
    }
}
