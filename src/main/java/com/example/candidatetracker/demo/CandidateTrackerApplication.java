package com.example.candidatetracker.demo;

import com.example.candidatetracker.demo.entity.Role;
import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

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

	@Transactional
	@GetMapping("/hello")
	public Role sayHello() {
		Session session = entityManager.unwrap(Session.class);
		User user = new User("hitanshu98@gmail.com", "asdfghj","Hitanshu", "Shah", "9869710860", true);
		Role role = session.get(Role.class, "ADMIN");
		//user.setRole(role);
		//user.setManager(null);
		//session.saveOrUpdate(user);
		session.close();
		//role.getUsers().add(user);
		//Query<User> query = session.createQuery("from User", User.class);
		//List<User> userList = query.getResultList();
		//session.close();
		return role;
	}

}
