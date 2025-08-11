package com.mtcoding.boardproject.domain;

import com.mtcoding.boardproject.domain.board.Board;
import com.mtcoding.boardproject.domain.board.BoardJpaRepository;
import com.mtcoding.boardproject.domain.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardJpaRepository.class)
@DataJpaTest
public class BoardJpaRepositoryTest {

    @Autowired
    private BoardJpaRepository boardJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    public void findById_test() {
        int id = 1;

        Board board = boardJpaRepository.findById(id);

        // lazy 전략
        System.out.println("lazy start");
        System.out.println(board.getUser().getUsername());
    }

    @Test
    public void save_test() {
        User user = new User(1, "ssar", "1234", "ssar@nate.com");
        Board board = new Board(null, "title", "content", user);
        boardJpaRepository.save(board);
        System.out.println(board.getId());
    }

    @Test
    public void update_test() {
        Board findboard = boardJpaRepository.findById(1);

        // findBoard 상태변경하기
        findboard.update("title change", "content change");
        em.flush();

//        Board updateBoard = boardJpaRepository.update(findboard);
//
//        System.out.println(updateBoard.getId());
//        System.out.println(updateBoard.getTitle());
//        System.out.println(updateBoard.getContent());
    }

    @Test
    public void deleteById_test() {
        int id = 1;
        boardJpaRepository.deleteById(id);
    }

    @Test
    public void findAllV2_test() {
        // given

        // when
        List<Board> boardList = boardJpaRepository.findAllV2();
        // Lazy loading
        System.out.println("lazy loading start");
        String email = boardList.get(0).getUser().getEmail();
        System.out.println("email: " + email);
    }

    @Test
    public void findAll_test() {
        // given

        // when
        List<Board> boardList = boardJpaRepository.findAll();
        // Lazy loading
//        System.out.println("lazy loading start");
//        String email = boardList.get(0).getUser().getEmail();
//        System.out.println("email: " + email);
    }

}
