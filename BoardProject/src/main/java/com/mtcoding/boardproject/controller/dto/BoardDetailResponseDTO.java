package com.mtcoding.boardproject.controller.dto;

import lombok.Data;

@Data
public class BoardDetailResponseDTO {
    private int id;
    private String title;
    private String content;

    private int userId;
    private String username;
}
