package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Duration;
import com.example.candidatetracker.demo.entity.Stats;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.http.ResponseEntity;

public interface StatsDAO {

    public ResponseEntity<Stats> getGlobalStats(Duration duration);

    public ResponseEntity<Stats> getLocalStats(Duration duration, User user);
}
