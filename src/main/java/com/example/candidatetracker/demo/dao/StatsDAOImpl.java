package com.example.candidatetracker.demo.dao;

import com.example.candidatetracker.demo.entity.Duration;
import com.example.candidatetracker.demo.entity.Stats;
import com.example.candidatetracker.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Set;

@Repository
public class StatsDAOImpl implements StatsDAO {

    public StatsDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    @Override
    public ResponseEntity<Stats> getGlobalStats(Duration duration) {
        if (duration.getStart() != null && duration.getEnd() != null)
            if (duration.getStart().getTime() > duration.getEnd().getTime())
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if (duration.getDays() == null && duration.getStart() == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Stats stats = new Stats();
        try {
            if (duration.getDays() != null) {
                stats.setHired(getGlobalByDays(duration, "hired"));
                stats.setHold(getGlobalByDays(duration, "hold"));
                stats.setReady(getGlobalByDays(duration, "ready"));
                stats.setRejected(getGlobalByDays(duration, "rejected"));
                stats.setTotal(getGlobalTotalByDays(duration));
            } else {
                stats.setHired(getGlobalByStartEnd(duration, "hired"));
                stats.setHold(getGlobalByStartEnd(duration, "hold"));
                stats.setReady(getGlobalByStartEnd(duration, "ready"));
                stats.setRejected(getGlobalByStartEnd(duration, "rejected"));
                stats.setTotal(getGlobalTotalByStartEnd(duration));
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Stats>(stats, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Stats> getLocalStats(Duration duration, User user) {
        if (duration.getStart() != null && duration.getEnd() != null)
            if (duration.getStart().getTime() > duration.getEnd().getTime())
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if (duration.getDays() == null && duration.getStart() == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Stats stats = new Stats();
        try {
            if (duration.getDays() != null) {
                stats.setHired(getLocalByDays(duration, "hired", user));
                stats.setHold(getLocalByDays(duration, "hold", user));
                stats.setReady(getLocalByDays(duration, "ready", user));
                stats.setRejected(getLocalByDays(duration, "rejected", user));
                stats.setTotal(getLocalTotalByDays(duration, user));
            } else {
                stats.setHired(getLocalByStartEnd(duration, "hired", user));
                stats.setHold(getLocalByStartEnd(duration, "hold", user));
                stats.setReady(getLocalByStartEnd(duration, "ready", user));
                stats.setRejected(getLocalByStartEnd(duration, "rejected", user));
                stats.setTotal(getLocalTotalByStartEnd(duration, user));
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Stats>(stats, HttpStatus.OK);
    }

    private long getGlobalByStartEnd(Duration duration, String status) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("select COUNT(c) from Candidate c where ( c.status = :status ) AND (c.lastUpdated between :start and :end )")
                .setParameter("status", status)
                .setParameter("start", duration.getStart())
                .setParameter("end", duration.getEnd());
        Long count = (Long) query.getSingleResult();
        return count;
    }

    private long getGlobalByDays(Duration duration, String status) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("select COUNT(c) from Candidate c where ( c.status = :status) AND (c.lastUpdated > current_date - :days) ")
                .setParameter("status", status)
                .setParameter("days", duration.getDays());
        Long count = (Long) query.getSingleResult();
        return count;
    }

    private long getLocalByStartEnd(Duration duration, String status, User currentUser) {
        Session session = entityManager.unwrap(Session.class);
        int userId = currentUser.getId();
        User user = session.get(User.class, userId);
        Set<User> successors = user.getSuccessors();
        Query query = session.createQuery("select COUNT(c) from Candidate c where c.recruiter in :successor and (c.status = :status) and (c.lastUpdated between :start and :end )")
                .setParameter("successor", successors)
                .setParameter("status", status)
                .setParameter("start", duration.getStart())
                .setParameter("end", duration.getEnd());
        Long count = (Long) query.getSingleResult();
        return count;
    }

    private long getLocalByDays(Duration duration, String status, User currentUser) {
        Session session = entityManager.unwrap(Session.class);
        int userId = currentUser.getId();
        User user = session.get(User.class, userId);
        Set<User> successors = user.getSuccessors();
        Query query = session.createQuery("select COUNT(c) from Candidate c where c.recruiter in :successor and (c.status = :status) and (c.lastUpdated > current_date - :days)")
                .setParameter("successor", successors)
                .setParameter("status", status)
                .setParameter("days", duration.getDays());
        Long count = (Long) query.getSingleResult();
        return count;
    }

    private long getLocalTotalByDays(Duration duration, User currentUser) {
        Session session = entityManager.unwrap(Session.class);
        int userId = currentUser.getId();
        User user = session.get(User.class, userId);
        Set<User> successors = user.getSuccessors();
        Query query = session.createQuery("select COUNT(c) from Candidate c where c.recruiter in :successor and (c.lastUpdated > current_date - :days)")
                .setParameter("successor", successors)
                .setParameter("days", duration.getDays());
        Long count = (Long) query.getSingleResult();
        return count;
    }

    private long getLocalTotalByStartEnd(Duration duration, User currentUser) {
        Session session = entityManager.unwrap(Session.class);
        int userId = currentUser.getId();
        User user = session.get(User.class, userId);
        Set<User> successors = user.getSuccessors();
        Query query = session.createQuery("select COUNT(c) from Candidate c where c.recruiter in :successor and (c.lastUpdated between :start and :end )")
                .setParameter("successor", successors)
                .setParameter("start", duration.getStart())
                .setParameter("end", duration.getEnd());
        Long count = (Long) query.getSingleResult();
        return count;
    }

    private long getGlobalTotalByDays(Duration duration) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("select COUNT(c) from Candidate c where (c.lastUpdated > current_date - :days) ")
                .setParameter("days", duration.getDays());
        Long count = (Long) query.getSingleResult();
        return count;
    }

    private long getGlobalTotalByStartEnd(Duration duration) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("select COUNT(c) from Candidate c where (c.lastUpdated between :start and :end )")
                .setParameter("start", duration.getStart())
                .setParameter("end", duration.getEnd());
        Long count = (Long) query.getSingleResult();
        return count;
    }

}
