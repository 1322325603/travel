package com.group_8.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CheckCodeVo {
    private String CodeId;
    private String checkCodeBase64;
}
