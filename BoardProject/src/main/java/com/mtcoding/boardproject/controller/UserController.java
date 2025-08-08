package com.mtcoding.boardproject.controller;

import ch.qos.logback.core.model.Model;
import com.mtcoding.boardproject.controller.dto.JoinRequestDTO;
import com.mtcoding.boardproject.controller.dto.LoginRequestDTO;
import com.mtcoding.boardproject.domain.user.User;
import com.mtcoding.boardproject.domain.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final HttpSession session;  // IoC에 등록되어 있음.

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping ("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @PostMapping("/join")
    public String join(JoinRequestDTO reqDTO) {
        userService.회원가입(reqDTO);
        return "redirect:/login-form";
    }

    @PostMapping("login") // 조회인데, Post는 로그인 밖에 없다. 예외 (URL이 쿼리스트링으로 정보 전달을 안할라고)
    public String Login(LoginRequestDTO reqDTO) {
        User sessionUser = userService.로그인(reqDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/board";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();  // 통으로 날리는 것
//        session.removeAttribute("sessionUser");  // key값만 날리는거
        return "redirect:/board";
    }
}
