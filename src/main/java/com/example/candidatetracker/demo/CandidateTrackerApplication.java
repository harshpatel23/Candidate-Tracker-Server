package com.example.candidatetracker.demo;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.Interview;
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


	@Transactional
	@GetMapping("/hello")
	public List<User> sayHello() {
		Session session = entityManager.unwrap(Session.class);
		//addCandidate();
		//User user = new User("hitanshu98@gmail.com", "asdfghj","Hitanshu", "Shah", "9869710860");
		//Role role = session.get(Role.class, "admin");
		//user.setRole(role);
		//Skill skill = new Skill("Java");
		//User user = session.get(User.class, 7);
		//Skill skill = session.get(Skill.class, 3);
		//Candidate candidate = new Candidate("candiadte@gamil.com",user, "candidate1", "musk", "9224601369", "Mumbai", "Pune", 6, 7, "linkedIn");
		//user.getSkills().add(skill);
		//skill.getCandidates().add(candidate);
		//session.save(candidate);
		//user.getSkills().add(skill);
		//skill.getInterviewers().add(user);
		//session.save(skill);
		//Query query = session.createQuery("from User", User.class);
		//List<User> users = query.getResultList();
		//user.setManager(null);
		//session.saveOrUpdate(user);
		//role.getUsers().add(user);
		//List<User> users = user.getSuccessors();
		Query<User> query = session.createQuery("from User", User.class);
		List<User> userList = query.getResultList();
		//Candidate candidate = session.find(Candidate.class, 1);
		//User recruiter = candidate.getRecruiter();
		//session.close();
		return userList;
	}

	private void addCandidate()
	{
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, 7);
		User ops = session.get(User.class, 4);
		Candidate candidate = session.get(Candidate.class, 2);
		Interview interview = new Interview(null, null, "Poor", 1);
		candidate.getInterviews().add(interview);
		interview.setCandidate(candidate);
		user.getCandidates().add(candidate);
		interview.setInterviewer(user);
		user.getInterviews().add(interview);
		interview.setUpdatedBy(ops);
		ops.getInterviewFeedbackUpdates().add(interview);
		session.save(interview);
		session.close();
	}

}
