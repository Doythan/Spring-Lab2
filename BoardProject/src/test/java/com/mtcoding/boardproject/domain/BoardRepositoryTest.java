package com.mtcoding.boardproject.domain;

import com.mtcoding.boardproject.domain.board.Board;
import com.mtcoding.boardproject.domain.board.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findAll_test() {
        // given
        // when
        List<Board> boardList = boardRepository.findAll();
        // eye
        for (Board board : boardList) {
            System.out.println(board.getId());
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
            System.out.println("-----------------");
        }
    }

    @Test
    public void findById_test() {
        // given
        int id = 6;

        // when
        Board board = boardRepository.findById(id);

        // eye (눈으로 검증하기)
        if (board == null) {
            System.out.println("조회가 안됐어 ! 그 번호 없나봐");
        } else {
            System.out.println(board.getId());
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
        }
    }

    @Test
    public void findAllV2_test() {
        // given
        // when
        List<Board> boardList = boardRepository.findAllV2();
        for (Board board : boardList) {
            System.out.println(board.getId());
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
        }
    }

    @Test
    public void findByIdV2_test() {
        // given
        int id = 2;

        // when
        Board board = boardRepository.findByIdV2(id);

        // eye (눈으로 검증하기)
        if (board == null) {
            System.out.println("조회가 안됐어 ! 그 번호 없나봐");
        } else {
            System.out.println(board.getId());
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
        }
    }

    @Test
    public void save_test() {
        // given
        String title = "제목5";
        String content = "내용5";

        // when
        boardRepository.save(title, content);

        // eye
        Board findBoard = boardRepository.findById(5);
        System.out.println(findBoard.getTitle());
        System.out.println(findBoard.getContent());
    }

    @Test
    public void update_test() {
        int id = 1;
        String title = "제목1 수정완료";
        String content = "내용1 수정완료";

        boardRepository.update(id, title, content);

        Board findBoard = boardRepository.findById(1);
        System.out.println(findBoard.getTitle());
        System.out.println(findBoard.getContent());
    }

    @Test
    public void deleteById_test() {
        // give
        int id = 0;

        // when
        boardRepository.deleteById(id);

        // eye
        // 검증 없어졌는지 전체를 확인
        List<Board> boardList = boardRepository.findAll();
        for (Board board : boardList) {
            System.out.println(board.getId());
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
        }
        // 사이즈가 하나 줄었는지만 확인
        System.out.println(boardList.size());
    }
}
