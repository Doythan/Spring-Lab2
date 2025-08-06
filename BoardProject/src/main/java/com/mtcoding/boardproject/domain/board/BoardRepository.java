package com.mtcoding.boardproject.domain.board;

import com.mtcoding.boardproject.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // Lombok: final 필드(em)를 파라미터로 받는 생성자 자동 생성 → 생성자 주입(DI) 편의
@Repository              // 스프링 컴포넌트 스캔 대상으로 등록 + JPA 예외를 스프링 DataAccessException으로 변환
public class BoardRepository {

    // JPA의 영속성 컨텍스트에 접근하는 핵심 인터페이스
    // 스프링이 트랜잭션 범위에 맞춰 프록시 EntityManager를 주입해줌(스레드-세이프하게 사용 가능)
    private final EntityManager em;

    public List<Board> findAll() {
        // 네이티브 SQL 실행 (엔티티가 아닌, DB 테이블/컬럼 명을 그대로 사용)
        Query query = em.createNativeQuery(
                "select id, title, content, user_id from board_tb order by id desc");

        // 네이티브 쿼리의 기본 반환 타입은 Object[] (각 로우가 컬럼 배열)
        List<Object[]> obsList = query.getResultList();

        // Object[] → 도메인 객체(Board)로 수동 매핑
        List<Board> boardList = new ArrayList<>();
        for (Object[] obs : obsList) {
            // 컬럼 순서와 타입을 정확히 맞춰 캐스팅해야 함
            // DB/드라이버에 따라 숫자 타입이 Long/BigInteger로 올 수 있으므로 주의
            Integer v1 = (Integer) obs[0]; // id
            String v2 = (String)  obs[1];  // title
            String v3 = (String)  obs[2];  // content
            Integer v4 = (Integer)  obs[3];

            User user = new User(v4, null, null, null);

            // (전제) Board에 (Integer, String, String)을 받는 생성자가 있어야 함
            Board board = new Board(v1, v2, v3, user);
            boardList.add(board);
        }
        return boardList;
    }

    // DB에서 id로 조회해서 Board로 매핑해서 리턴하기
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

    // 게시글 저장 (네이티브 SQL 사용)
    @Transactional // 데이터 변경 쿼리는 트랜잭션 안에서 수행되어야 함
    public void save(String title, String content, int userId) {
        // createNativeQuery(sql):
        // - 네이티브 SQL을 실행하기 위해 Query 객체를 생성
        // - INSERT/UPDATE/DELETE 처럼 "결과 집합(ResultSet)이 없는" 쿼리는
        //   엔티티 매핑 클래스(Board.class) 지정이 필요 없다.
        Query query = em.createNativeQuery(
                "insert into board_tb (title, content, userId) values (?, ?, ?)"
        );

        // 위치 기반 파라미터 바인딩 (JPA는 1부터 시작)
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, userId);

        // executeUpdate():
        // - INSERT/UPDATE/DELETE 수행 시 영향받은 행 수(int)를 반환
        // - SELECT가 아니므로 getResultList()/getSingleResult()가 아니라 executeUpdate()를 호출
        query.executeUpdate();
    }

    public int deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);
        return query.executeUpdate();
    }

    public int update(int id, String title, String content) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);
        return query.executeUpdate();
    }

    /* Service 만들면서 코드 수정
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    public void update(int id, String title, String content) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);
        query.executeUpdate();
    }
     */
}
