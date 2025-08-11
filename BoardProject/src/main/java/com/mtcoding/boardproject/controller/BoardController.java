package com.mtcoding.boardproject.controller;

import com.mtcoding.boardproject.controller.dto.BoardDetailResponseDTO;
import com.mtcoding.boardproject.controller.dto.BoardListResponseDTO;
import com.mtcoding.boardproject.controller.dto.BoardSaveRequestDTO;
import com.mtcoding.boardproject.controller.dto.BoardUpdateRequestDTO;
import com.mtcoding.boardproject.domain.board.BoardService;
import com.mtcoding.boardproject.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 게시판 화면을 담당하는 컨트롤러
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    // 게시글 목록 페이지 컨트롤러
    @GetMapping("/board")
    public String list(HttpServletRequest request) {

        // 서비스 계층 호출:
        // - Controller는 비즈니스 로직을 직접 하지 않고 Service에 위임한다.
        // - boardService.게시글목록()은 엔티티 목록을 조회하고
        //   화면에 필요한 필드만 담은 DTO(List<BoardListResponseDTO>)로 변환해 반환.
        List<BoardListResponseDTO> resDTO = boardService.게시글목록();

        // 뷰(Model)에 데이터 바인딩:
        // - request.setAttribute("models", respDTO)로 뷰 템플릿에서 접근 가능
        // - Mustache: {{#models}} ... {{/models}}
        request.setAttribute("models", resDTO);

        // 뷰 이름 반환:
        // - templates/board/list.mustache 렌더링
        return "board/list";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        BoardDetailResponseDTO resDTO = boardService.게시글상세(id);
        request.setAttribute("model", resDTO);
        return "board/detail";
    }

    /* Service가 만들어지면서 주석처리 하였음
    // GET /board 요청이 들어오면 게시글 목록 페이지를 반환
    @GetMapping("/board")
    public String list(HttpServletRequest request) {
        // 화면에 내려줄 목록 데이터를 담을 리스트 (실습용 목 데이터)
        List<BoardListResponseDTO> resDTO = new ArrayList<>();

        // 더미 DTO 객체 3개 생성
        BoardListResponseDTO boardList1 = new BoardListResponseDTO();
        BoardListResponseDTO boardList2 = new BoardListResponseDTO();
        BoardListResponseDTO boardList3 = new BoardListResponseDTO();

        // 각 더미 객체에 ID 세팅
        boardList1.setId(1);
        boardList2.setId(2);
        boardList3.setId(3);

        // 각 더미 객체에 제목 세팅 (실습용 텍스트)
        boardList1.setTitle("제목1: 도경원 천재");
        boardList2.setTitle("제목2: 도경원 바보");
        boardList3.setTitle("제목3: 열심히 박으면 결국은 다 된다.");

        // 리스트에 더미 객체들을 순서대로 추가
        resDTO.add(boardList1);
        resDTO.add(boardList2);
        resDTO.add(boardList3);

        // 뷰 템플릿에서 사용할 모델 속성으로 등록
        // - Mustache에서 {{#models}} ... {{/models}} 로 반복 렌더링 가능
        request.setAttribute("models", resDTO);

        // resources/templates/board/list.mustache 로 포워딩
        return "board/list";
    }

    // 게시글 상세 페이지를 처리하는 컨트롤러 메서드
    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        // @PathVariable:
        // URL 경로에 있는 {id} 값을 변수로 받아오는 어노테이션
        // 예: /board/3 요청 → id = 3

        // HttpServletRequest:
        // 서블릿 기반 요청 객체로, 키-값 형태로 데이터를 뷰(View)에 전달할 수 있음
        // request.setAttribute("key", value) → 뷰 템플릿에서 ${key} 로 사용 가능 (Mustache에서는 {{key}})

        // 화면에 전달할 상세 DTO 객체 생성
        BoardDetailResponseDTO resDTO = new BoardDetailResponseDTO();

        // 더미 데이터 생성 (실제 DB 연동 전 테스트용)
        resDTO.setId(id);
        resDTO.setTitle("도경원 자바공부" + id);
        resDTO.setContent("자바에 대해 열심을 다해 공부한 내용들 ~~" + id);

        // 모델에 'model'이라는 키로 DTO 바인딩 → mustache에서 {{model.title}}, {{model.content}} 등으로 사용
        request.setAttribute("model", resDTO);

        // templates/board/detail.mustache 뷰 페이지로 이동
        return "board/detail";
    }
     */

    @GetMapping("/board/save-form")
    public String saveForm() { return "board/save-form"; }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) throw new RuntimeException("로그인 하세요");

        BoardDetailResponseDTO resDTO = boardService.게시글상세(id);
        request.setAttribute("model", resDTO);
        return "board/update-form";
    }

    // 게시글 저장
    @PostMapping("/board/save")
    public String save(BoardSaveRequestDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) throw new RuntimeException("로그인 하세요");
        boardService.게시글쓰기(reqDTO, sessionUser);
        return "redirect:/board";
    }

    // 게시글 삭제
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) throw new RuntimeException("로그인 하세요");

        boardService.게시글삭제(id, sessionUser);
        return "redirect:/board";
    }

    // 게시글 수정
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") int id, BoardUpdateRequestDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) throw new RuntimeException("로그인 하세요");
        boardService.게시글수정(id, reqDTO, sessionUser);
        return "redirect:/board/" + id;
    }

    /* Service 구현해서 주석 처리합니다.
    // 게시글 저장
    @PostMapping("/board/save")
    public String save(BoardSaveRequestDTO reqDTO) {
        // @PostMapping: 폼 전송(POST) 요청을 처리
        // 파라미터 바인딩: 요청 파라미터 이름과 DTO의 필드명이 일치하면
        //                  스프링이 자동으로 reqDTO에 바인딩해 준다.
        // 예) title=...&content=... -> BoardSaveRequestDTO(title, content)

        // TODO: reqDTO 검증(@Valid 사용), 엔티티로 매핑 후 repository.save()

        // redirect:/board → 저장 후 목록으로 이동 (PRG 패턴: 새로고침 중복 전송 방지)
        return "redirect:/board";
    }

    // 게시글 삭제
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        // @PathVariable("id"): URL 경로의 {id} 값을 메서드 파라미터로 매핑
        // 예) POST /board/3/delete -> id = 3

        // TODO: repository.deleteById(id) 실행
        // 삭제 후 목록으로 리다이렉트
        return "redirect:/board";
    }

    // 게시글 수정
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") int id, BoardUpdateRequestDTO reqDTO) {
        // @PathVariable: 경로의 식별자 수신
        // BoardUpdateRequestDTO: 수정 폼의 입력값(title, content 등)이 자동 바인딩

        // TODO: repository.findById(id) -> 엔티티 조회 후 필드 수정 -> 저장
        // 수정 후 상세 페이지로 리다이렉트하여 변경 내용 확인
        return "redirect:/board/" + id;
    }
     */

}

