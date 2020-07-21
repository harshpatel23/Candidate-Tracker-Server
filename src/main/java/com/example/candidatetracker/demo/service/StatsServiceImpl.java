package com.example.candidatetracker.demo.service;

import com.example.candidatetracker.demo.dao.StatsDAO;
import com.example.candidatetracker.demo.entity.Duration;
import com.example.candidatetracker.demo.entity.Stats;
import com.example.candidatetracker.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    public StatsServiceImpl(StatsDAO statsDAO) {
        this.statsDAO = statsDAO;
    }

    private StatsDAO statsDAO;

    @Override
    public ResponseEntity<Stats> getGlobalStats(Duration duration) {
        return this.statsDAO.getGlobalStats(duration);
    }

    @Override
    public ResponseEntity<Stats> getLocalStats(Duration duration, User user) {
        return this.statsDAO.getLocalStats(duration, user);
    }
}
