package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.entity.Duration;
import com.example.candidatetracker.demo.entity.Stats;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.http.ResponseEntity;

public interface StatsService {

    ResponseEntity<Stats> getGlobalStats(Duration duration);

    public ResponseEntity<Stats> getLocalStats(Duration duration, User user);
}
