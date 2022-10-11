package com.example.noticeboard.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserForm {
    @NotEmpty(message = "닉네임은 필수입니다!")
    private String nickname;
    @NotEmpty(message = "비밀번호는 필수입니다!")
    private String password;
}
