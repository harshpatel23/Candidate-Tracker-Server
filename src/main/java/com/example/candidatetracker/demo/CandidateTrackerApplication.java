package com.example.candidatetracker.demo;

import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootApplication
@RestController
public class CandidateTrackerApplication {

	private EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(CandidateTrackerApplication.class, args);
	}

	public CandidateTrackerApplication(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
