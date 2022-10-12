package com.example.noticeboard.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ContentForm {
    @NotEmpty(message = "제목은 필수입니다!")
    private String title;

    private String text;

    private String status;
    private Long userId;
    private Long categoryId;
}
