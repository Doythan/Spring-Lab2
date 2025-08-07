package com.mtcoding.boardproject.domain.user;

import com.mtcoding.boardproject.controller.dto.JoinRequestDTO;
import com.mtcoding.boardproject.controller.dto.LoginRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void 회원가입(JoinRequestDTO reqDTO) {
        // 1. username 중복 확인
        User findUser = userJpaRepository.findByUsername(reqDTO.getUsername());
        if (findUser != null) {
            throw new RuntimeException("username이 존재합니다 : " + reqDTO.getUsername());
        }
        // 2. insert
        User user = new User(null, reqDTO.getUsername(), reqDTO.getPassword(), reqDTO.getEmail());
        userJpaRepository.save(user);
    }

    public User 로그인(LoginRequestDTO reqDTO) {
        User findUser = userJpaRepository.findByUsername(reqDTO.getUsername());

        if (findUser == null) throw new RuntimeException("username이 틀렸습니다.");

        if (!findUser.getPassword().equals(reqDTO.getPassword())) throw new RuntimeException("password가 틀렸습니다.");

        return findUser;
    }
}
