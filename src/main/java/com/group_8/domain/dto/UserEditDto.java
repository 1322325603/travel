package com.group_8.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserEditDto {
    private Integer id;
    private String name;
    private String password;
    private String telephone;
}
