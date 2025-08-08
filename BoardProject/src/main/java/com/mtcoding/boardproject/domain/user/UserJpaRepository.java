package com.mtcoding.boardproject.domain.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserJpaRepository {
    private final EntityManager em;

    public User findById(int id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {

        try {
            Query query = em.createQuery("select u from User u where u.username = :username", User.class);
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(User user) {
        em.persist(user);
    }
}
