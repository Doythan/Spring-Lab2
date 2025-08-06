package com.mtcoding.boardprojectdemo.controller;

import com.mtcoding.boardprojectdemo.controller.dto.BoardDetailResponseDTO;
import com.mtcoding.boardprojectdemo.controller.dto.BoardListResponseDTO;
import com.mtcoding.boardprojectdemo.controller.dto.BoardSaveRequestDTO;
import com.mtcoding.boardprojectdemo.controller.dto.BoardUpdateRequestDTO;
import com.mtcoding.boardprojectdemo.domain.board.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String list(HttpServletRequest request) {
        List<BoardListResponseDTO> resDTO = boardService.게시글목록();
        request.setAttribute("models", resDTO);
        return "board/list";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request) {
        BoardDetailResponseDTO resDTO = boardService.게시글상세(id);
        request.setAttribute("model", resDTO);
        return "board/detail";
    }

    @PostMapping("/board/save")
    public String save(BoardSaveRequestDTO reqDTO) {
        boardService.게시글쓰기(reqDTO);
        return "redirect:/board";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        boardService.게시글삭제(id);
        return "redirect:/board";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") int id, BoardUpdateRequestDTO reqDTO) {
        boardService.게시글수정(id, reqDTO);
        return "redirect:/board/" + id;
    }

    @GetMapping("/board/save-form")
    public String saveForm() { return "board/save-form"; }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        BoardDetailResponseDTO resDTO = boardService.게시글상세(id);
        request.setAttribute("model", resDTO);
        return "board/update-form";
    }
}
