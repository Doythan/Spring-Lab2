package com.mtcoding.boardproject.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 기본자형 안쓴다 null을 못넣기 때문에 int 안씀

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String email;


    public User(Integer id,String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
