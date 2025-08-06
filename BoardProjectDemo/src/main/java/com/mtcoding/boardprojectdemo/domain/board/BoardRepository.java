package com.mtcoding.boardprojectdemo.domain.board;

import com.mtcoding.boardprojectdemo.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAll() {
        Query query = em.createNativeQuery("select id, title, content, user_id from board_tb order by id desc ");
        List<Object[]> obsList = query.getResultList();
        List<Board> boardList = new ArrayList<>();
        for (Object[] obs : obsList) {
            Integer v1 = (Integer) obs[0];
            String v2 = (String) obs[1];
            String v3 = (String) obs[2];
            Integer v4 = (Integer) obs[3];

            User user = new User(v4, null, null, null);
            Board board = new Board(v1, v2, v3, user);
            boardList.add(board);
        }
        return boardList;
    }

    public Board findById(int id) {
        String sql = """
                select bt.id, bt.title, bt.content, ut.id, ut.username, ut.password, ut.email
                from board_tb bt inner join user_tb ut on bt.user_id = ut.id
                where bt.id = ?
                """;
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, id);

        try {
            Object[] obs = (Object[]) query.getSingleResult();
            Integer v1 = (Integer) obs[0];
            String v2 = (String) obs[1];
            String v3 = (String) obs[2];
            Integer v4 = (Integer) obs[3];
            String v5 = (String) obs[4];
            String v6 =  (String) obs[5];
            String v7 = (String) obs[6];

            // ORM -> 왜? 객체로 관리하는게 좋다.
            User user = new User(v4, v5, v6, v7);
            Board board = new Board(v1, v2, v3, user);
            return board;
        } catch (RuntimeException e) {
            return null;
        }
    }

    public Board findByIdV2(int id) {
        Query query = em.createQuery("select b from Board b join fetch b.user where b.id = :id", Board.class);
        query.setParameter("id", id);
        return (Board) query.getSingleResult();
    }

    @Transactional
    public void save(String title, String content, int user_id) {
        Query query = em.createNativeQuery("insert into board_tb (title, content, user_id) values (?, ?, ?)");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, user_id);
        query.executeUpdate();
    }

    @Transactional
    public int deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);
        return query.executeUpdate();
    }

    @Transactional
    public int updateById(int id, String title, String content) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);
        return query.executeUpdate();
    }
}
