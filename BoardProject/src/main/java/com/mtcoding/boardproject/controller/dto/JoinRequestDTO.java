package com.mtcoding.boardproject.controller.dto;

import lombok.Data;

@Data // getter, setter, toString
public class JoinRequestDTO {
    private String username;
    private String password;
    private String email;
}
