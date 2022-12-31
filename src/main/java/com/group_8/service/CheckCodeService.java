package com.group_8.service;

import com.group_8.domain.ResponseResult;
import com.group_8.domain.vo.CheckCodeVo;



public interface CheckCodeService {
    /**
     * 获取验证码
     *
     * @param codeId 验证码ID
     * @return 验证码id、验证码图片base64
     */
    ResponseResult<CheckCodeVo> getCheckCode(String codeId);

    boolean checkCode(String codeId, String code);
}
