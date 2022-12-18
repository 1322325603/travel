package com.yozuru.service;

import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.vo.CheckCodeVo;

/**
 * @author Yozuru
 */

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
