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

    public List<Board> findAllV2() {
        Query query = em.createQuery("select b from Board b join fetch b.user order by b.id desc", Board.class);
        return query.getResultList();
    }

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by id desc", Board.class);
        return query.getResultList();
    }

    public Board findById(int id) {
        return em.find(Board.class, id);
    }

    public Board findByIdJoinUser(int id) {
        Query query = em.createQuery("select b from Board b join fetch b.user where b.id = :id", Board.class);
        query.setParameter("id", id);
        return (Board) query.getSingleResult();
    }

    public Board save(Board board) {  // insert, select maxid 찾고, Board 매핑 해줌.
        em.persist(board);
        return board;
    }

    public Board update(Board board) {
        Query query = em.createQuery("update Board b set b.title =:title, b.content=:content where b.id=:id");
        query.setParameter("title", board.getTitle());
        query.setParameter("content", board.getContent());
        query.setParameter("id", board.getId());
        query.executeUpdate();
        return board;
    }

    public void deleteById(int id) {
        Query query = em.createQuery("delete Board b where b.id=:id");
        query.setParameter("id", id);
        int result = query.executeUpdate();  // -1, 변경된행의개수, 0

        if (result != 1) {
            throw new RuntimeException("잘못된 삭제 요청을 했어요");
        }
    }
}
