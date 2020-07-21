package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Candidate;
import com.example.candidatetracker.demo.entity.File;
import com.example.candidatetracker.demo.entity.Skill;
import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public class CandidateDAOImpl implements CandidateDAO {

    @Autowired
    public CandidateDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    @Override
    public ResponseEntity<List<Candidate>> getAll(User currentUser) {
        int userId = currentUser.getId();
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);

        Set<User> successors = user.getSuccessors();

        Query query = session.createQuery("select c from Candidate c where c.recruiter in :successor", Candidate.class)
                .setParameter("successor", successors);

        List<Candidate> candidates = query.list();
        if (candidates == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Candidate> save(Candidate candidate) {
        Session session = entityManager.unwrap(Session.class);

        if (getCandidateByEmail(candidate.getEmail()).getStatusCodeValue() == 200) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        candidate.setCurrentRound(0);
        candidate.setStatus("ready");
        candidate.setLastUpdated(new Date());
        for (Skill s : candidate.getSkillSet()) {
            Skill sessionSkill = session.get(Skill.class, s.getSkillId());
            if (sessionSkill == null)
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            sessionSkill.getCandidates().add(candidate);
        }
        session.save(candidate);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Candidate> update(Candidate candidate) {
        Session session = entityManager.unwrap(Session.class);
        Candidate myCandidate = session.get(Candidate.class, candidate.getId());
        Query query = session.createQuery("from Skill", Skill.class);
        List<Skill> skills = query.getResultList();
        Set<Skill> skillSet = candidate.getSkillSet();
        for (Skill s : skills) {
            if (skillSet.contains(s))
                s.getCandidates().add(myCandidate);
            else if (!skillSet.contains(s))
                s.getCandidates().remove(myCandidate);
        }
        myCandidate.setLastUpdated(new Date());
        //myCandidate.setSkillSet(skillSet);
        session.saveOrUpdate(myCandidate);
        return new ResponseEntity<>(myCandidate, HttpStatus.OK);
    }


    // Any signed in user can acees any candidate as of now
    @Override
    public ResponseEntity<Candidate> getCandidateById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Candidate candidate = session.get(Candidate.class, id);
        if (candidate != null)
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Any signed in user can acees any candidate as of now
    @Override
    public ResponseEntity<Candidate> getCandidateByEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        Query<Candidate> query = session
                .createQuery("select c from Candidate c where c.email = :email", Candidate.class)
                .setParameter("email", email);
        Candidate candidate;
        try {
            candidate = query.getSingleResult();
        } catch (NoResultException e) {
            candidate = null;
        }
        if (candidate != null)
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Candidate> changeCandidateStatus(Integer id, String status) {
        if (id == null || status == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Session session = entityManager.unwrap(Session.class);
        Candidate toBeModified = session.find(Candidate.class, id);
        if (toBeModified == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        toBeModified.setStatus(status);
        Date date = new Date();
        Date d = new Date(date.getTime());
        toBeModified.setLastUpdated(d);
        session.saveOrUpdate(toBeModified);
        return new ResponseEntity<>(toBeModified, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Candidate> uploadCV(int candidate_id, MultipartFile cvFile) {
        Session session = entityManager.unwrap(Session.class);

        Candidate candidate = session.get(Candidate.class, candidate_id);

        if (candidate == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        try {
            File cv = new File(cvFile.getOriginalFilename(), cvFile.getContentType(), cvFile.getBytes());

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = null;
            byte[] cvBytes = null;
            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(cv);
                out.flush();
                cvBytes = bos.toByteArray();
            } finally {
                try {
                    bos.close();
                } catch (IOException io) {
                    System.out.println(io.getMessage());
                }
            }

            if (cvBytes != null) {
                candidate.setCv(cvBytes);
            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        session.saveOrUpdate(candidate);

        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource> getCV(int id) {
        Session session = entityManager.unwrap(Session.class);

        if (session.get(Candidate.class, id).getCv() == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        byte[] cvBytes = session.get(Candidate.class, id).getCv();

        ByteArrayInputStream bis = new ByteArrayInputStream(cvBytes);
        ObjectInput in = null;
        File cvFile = null;

        try {
            try {
                in = new ObjectInputStream(bis);
                cvFile = (File) in.readObject();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException io) {
                    System.out.println(io.getMessage());
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

        if (cvFile == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(cvFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cvFile.getFileName() + "\"")
                .body(new ByteArrayResource(cvFile.getFileContent()));


    }

}
