package com.yozuru.domain.dto;

import lombok.Data;

/**
 * @author Yozuru
 */

@Data
public class UserLoginDto {
    private String userName;
    private String password;
    private String checkCode;
}
