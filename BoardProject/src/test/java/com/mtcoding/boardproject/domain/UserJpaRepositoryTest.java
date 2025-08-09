package com.mtcoding.boardproject.domain;

import com.mtcoding.boardproject.domain.user.User;
import com.mtcoding.boardproject.domain.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserJpaRepository.class)
@DataJpaTest  // EntityManager가 뜸, DB도 뜸
public class UserJpaRepositoryTest {

    @Autowired  // 생성자 주입을 안해도 DI를 해줌
    private UserJpaRepository userJpaRepository;

    @Test
    public void cache_test() {
        User u1 = userJpaRepository.findById(1);
        User u2 = userJpaRepository.findById(5);
    }
}
