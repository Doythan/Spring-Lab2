package com.mtcoding.boardproject.domain.board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 모든 필드의 Getter 메서드를 자동 생성 (Setter는 불변성 유지 위해 생략)
@NoArgsConstructor // JPA가 객체 생성 시 기본 생성자를 사용하므로 반드시 필요, @NoArgsConstructor는 “기본 생성자만 만든다”
@Table(name = "board_tb") // 매핑될 실제 테이블 이름을 명시 (기본은 클래스명으로 생성됨)
@Entity // JPA가 관리할 수 있는 엔티티 클래스임을 선언 (반드시 있어야 함)
public class Board {

    @Id // 해당 필드가 기본 키(primary key)임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본 키 값을 DB에서 자동 생성하도록 설정 (Auto Increment 방식)
    private Integer id;

    private String title; // 게시글 제목

    private String content; // 게시글 내용 (또는 설명)

    private Integer userId;  // FK

    // 생성자, Setter 없이 JPA가 알아서 관리 (Setter는 필요 시에만 명시적으로 생성)
    public Board(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
