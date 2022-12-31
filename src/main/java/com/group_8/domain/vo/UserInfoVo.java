package com.group_8.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserInfoVo {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String telephone;
    private String status;
}
