package com.example.candidatetracker.demo.rest;


import com.example.candidatetracker.demo.entity.Skill;
import com.example.candidatetracker.demo.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    private SkillService skillService;

    @RequestMapping("")
    public List<Skill> findAll()
    {
        return skillService.findAll();
    }

}
