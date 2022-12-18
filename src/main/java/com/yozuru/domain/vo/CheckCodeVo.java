package com.yozuru.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yozuru
 */
@Data
@AllArgsConstructor
public class CheckCodeVo {
    private String CodeId;
    private String checkCodeBase64;
}
