package com.mtcoding.boardprojectdemo.controller.dto;

import lombok.Data;

@Data
public class BoardUpdateRequestDTO {
    private int id;
    private String title;
    private String content;
}
