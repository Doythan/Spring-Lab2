package com.mtcoding.boardproject.domain.board;

import com.mtcoding.boardproject.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "board_tb")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; //FK

    public Board(Integer id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // 의미 있는 이름으로 getter를 만들기 (어노테이션 안붙이는 이유)
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}