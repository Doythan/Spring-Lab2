package com.mtcoding.boardprojectdemo.domain;

import com.mtcoding.boardprojectdemo.domain.board.Board;
import com.mtcoding.boardprojectdemo.domain.board.BoardRepository;
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
        List<Board> boardList = boardRepository.findAll();
        for (Board board : boardList) {
            System.out.println(board.getId());
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
        }
    }

    @Test
    public void findById_test() {
        int id = 1;
        Board board = boardRepository.findById(id);
        if (board == null) {
            System.out.println("조회실패 !");
        } else {
            System.out.println(board.getId());
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
            System.out.println(board.getUser().getId());
            System.out.println(board.getUser().getUsername());
            System.out.println(board.getUser().getPassword());
            System.out.println(board.getUser().getEmail());
        }
    }

    @Test
    public void findByIdV2_test() {
        int id = 1;

        Board board = boardRepository.findById(id);

        if (board == null) {
            System.out.println("조회가 안됐어 ! 그 번호 없나봐");
        } else {
            System.out.println(board.getId());
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
            System.out.println(board.getUser().getId());
            System.out.println(board.getUser().getUsername());
            System.out.println(board.getUser().getPassword());
            System.out.println(board.getUser().getEmail());
        }
    }


    @Test
    public void save_test() {
        String title = "제목6";
        String content = "내용6";

        boardRepository.save(title, content, 1);

        Board findBoard = boardRepository.findById(6);
        System.out.println(findBoard.getTitle());
        System.out.println(findBoard.getContent());
    }

    @Test
    public void deleteById_test() {
        int id = 5;
        boardRepository.deleteById(id);
        List<Board> boardList = boardRepository.findAll();
        for (Board board : boardList) {
            System.out.println(board.getId());
        }
    }

    @Test
    public void updateById_test() {
        int id = 3;
        String title = "수정 제목";
        String content = "수정 내용";
        boardRepository.updateById(id, title, content);
        List<Board> boardList = boardRepository.findAll();
        for (Board board : boardList) {
            System.out.println(board.getTitle());
        }
    }
}
