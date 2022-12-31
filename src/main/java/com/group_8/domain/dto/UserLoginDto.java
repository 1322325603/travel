package com.group_8.domain.dto;

import lombok.Data;


@Data
public class UserLoginDto {
    private String username;
    private String password;
    private String checkCode;
}
