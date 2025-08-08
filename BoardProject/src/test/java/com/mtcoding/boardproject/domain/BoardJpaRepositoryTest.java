package com.mtcoding.boardproject.domain;

import com.mtcoding.boardproject.domain.board.Board;
import com.mtcoding.boardproject.domain.board.BoardJpaRepository;
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

    @Test
    public void findAll_test() {
        // given

        // when
        List<Board> boardList = boardJpaRepository.findAll();
        // Lazy loading
        System.out.println("lazy loading start");
        String email = boardList.get(2).getUser().getEmail();
        System.out.println("email: " + email);
    }
}
