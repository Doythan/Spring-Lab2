package com.mtcoding.boardprojectdemo.domain.board;

import com.mtcoding.boardprojectdemo.controller.dto.BoardDetailResponseDTO;
import com.mtcoding.boardprojectdemo.controller.dto.BoardListResponseDTO;
import com.mtcoding.boardprojectdemo.controller.dto.BoardSaveRequestDTO;
import com.mtcoding.boardprojectdemo.controller.dto.BoardUpdateRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardListResponseDTO> 게시글목록() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardListResponseDTO> resDTO = new ArrayList<>();
        for (Board board : boardList) {
            BoardListResponseDTO boardListResponseDTO = new BoardListResponseDTO();
            boardListResponseDTO.setId(board.getId());
            boardListResponseDTO.setTitle(board.getTitle());
            resDTO.add(boardListResponseDTO);
        }
        return resDTO;
    }

    public BoardDetailResponseDTO 게시글상세(int id) {
        Board board = boardRepository.findById(id);
        BoardDetailResponseDTO resDTO = new BoardDetailResponseDTO();
        resDTO.setId(board.getId());
        resDTO.setTitle(board.getTitle());
        resDTO.setContent(board.getContent());
        return resDTO;
    }

    @Transactional
    public void 게시글쓰기(BoardSaveRequestDTO reqDTO) {
        boardRepository.save(reqDTO.getTitle(), reqDTO.getContent(), 1);
    }

    @Transactional
    public void 게시글삭제(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 게시글수정(int id, BoardUpdateRequestDTO reqDTO) {
        boardRepository.updateById(id, reqDTO.getTitle(), reqDTO.getContent());
    }
}
