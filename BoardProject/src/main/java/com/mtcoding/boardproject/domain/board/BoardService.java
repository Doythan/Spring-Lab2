package com.mtcoding.boardproject.domain.board;

import com.mtcoding.boardproject.controller.dto.BoardDetailResponseDTO;
import com.mtcoding.boardproject.controller.dto.BoardListResponseDTO;
import com.mtcoding.boardproject.controller.dto.BoardSaveRequestDTO;
import com.mtcoding.boardproject.controller.dto.BoardUpdateRequestDTO;
import com.mtcoding.boardproject.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // Lombok: final 필드(boardRepository)를 파라미터로 받는 생성자 자동 생성 → 생성자 주입
@Service                 // 비즈니스 로직을 담는 서비스 계층 컴포넌트로 스캔/등록
public class BoardService {

    private final BoardJpaRepository boardJpaRepository; // 데이터 접근 계층 의존성 (DI)

    /**
     * 게시글 목록 조회 서비스
     * 1) Repository에서 엔티티 목록을 조회
     * 2) 화면/응답에 필요한 최소 필드만 담은 DTO로 변환
     *    - 엔티티를 직접 노출하지 않고, 노출 스펙을 DTO로 통제(보안/유지보수)
     */
    public List<BoardListResponseDTO> 게시글목록() {
        // 1) DB에서 게시글 엔티티 목록 조회
        List<Board> boardList = boardJpaRepository.findAll();

        // 2) 엔티티 → 응답 DTO로 수동 매핑 (id, title만 노출)
        List<BoardListResponseDTO> resDTO = new ArrayList<>();
        for (Board board : boardList) {
            BoardListResponseDTO boardListResponseDTO = new BoardListResponseDTO();
            boardListResponseDTO.setId(board.getId());       // 식별자
            boardListResponseDTO.setTitle(board.getTitle()); // 제목
            resDTO.add(boardListResponseDTO);
        }
        return resDTO;
    }

    public BoardDetailResponseDTO 게시글상세(int id) {
        Board board = boardJpaRepository.findByIdJoinUser(id);  // bpard

        BoardDetailResponseDTO resDTO = new BoardDetailResponseDTO();
        resDTO.setId(board.getId());
        resDTO.setTitle(board.getTitle());
        resDTO.setContent(board.getContent());
        resDTO.setUserId(board.getUser().getId());
        resDTO.setUsername(board.getUser().getUsername());  // lazy loading 발동 (x) 이유는 조인했으니깐.

        return resDTO;
    }

    @Transactional
    public void 게시글쓰기(BoardSaveRequestDTO boardSaveRequestDTO, User sessionUser) {
        Board board = new Board(null, boardSaveRequestDTO.getTitle(), boardSaveRequestDTO.getContent(), sessionUser);
        boardJpaRepository.save(board);
    }

    @Transactional
    public void 게시글삭제(int id, User sessionUser) {
        Board findboard = boardJpaRepository.findById(id);

        if (findboard == null) {
            throw new RuntimeException("게시글을 찾을 수 없음 404");  // 자원을 찾을 수 없으면 404
        }

        if (findboard.getUser().getId() != sessionUser.getId()) {  // lazy loading을 안한다.
            throw new RuntimeException("권한 없음 403");
        }

        boardJpaRepository.deleteById(id);
    }

    @Transactional // 업데이트/삭제는 꼭 트랜잭션
    public void 게시글수정(int id, BoardUpdateRequestDTO boardSaveRequestDTO, User sessionUser) {
        Board findboard = boardJpaRepository.findById(id);

        if (findboard == null) {
            throw new RuntimeException("게시글을 찾을 수 없음 404");  // 자원을 찾을 수 없으면 404
        }

        if (findboard.getUser().getId() != sessionUser.getId()) {  // lazy loading을 안한다.
            throw new RuntimeException("권한 없음 403");
        }

        findboard.update(boardSaveRequestDTO.getTitle(), boardSaveRequestDTO.getContent());
        // 트랜잭션 종료시에 flush가 자동으로 일어나면서 더티체킹이 된다.
    }
}

