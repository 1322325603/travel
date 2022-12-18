package com.yozuru.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yozuru
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo {
    private String token;
    private String name;
}