package com.mtcoding.boardproject.domain.board;

import com.mtcoding.boardproject.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardJpaRepository {
    private final EntityManager em;

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by id desc", Board.class);
        return query.getResultList();
    }
}
