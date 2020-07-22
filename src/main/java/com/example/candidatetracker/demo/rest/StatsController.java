package com.example.candidatetracker.demo.rest;

import com.example.candidatetracker.demo.entity.Duration;
import com.example.candidatetracker.demo.entity.Stats;
import com.example.candidatetracker.demo.entity.User;
import com.example.candidatetracker.demo.service.JwtUserDetails;
import com.example.candidatetracker.demo.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    private StatsService statsService;


    @PostMapping("/local")
    ResponseEntity<Stats> displayLocalDuration(@RequestBody Duration duration, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        User user = null;
        if (principal instanceof JwtUserDetails) {
            user = ((JwtUserDetails) principal).getUser();
        }
        return this.statsService.getLocalStats(duration, user);
    }

    @PostMapping("/global")
    ResponseEntity<Stats> displayGlobalDuration(@RequestBody Duration duration) {
        return this.statsService.getGlobalStats(duration);
    }

}
