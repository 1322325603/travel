package com.group_8.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.group_8.domain.ResponseResult;
import com.group_8.domain.constant.RedisConstant;
import com.group_8.domain.vo.CheckCodeVo;
import com.group_8.service.CheckCodeService;
import org.springframework.stereotype.Service;


@Service
public class CheckCodeServiceImpl implements CheckCodeService {

    @CreateCache(area = "checkCode",name = RedisConstant.CHECK_CODE_KEY_PREFIX,cacheType = CacheType.REMOTE)
    private Cache<String, String> checkCodeCache;

    @Override
    public ResponseResult<CheckCodeVo> getCheckCode(String codeId) {
        //生成验证码
        SpecCaptcha captcha = new SpecCaptcha(100, 48, 4);
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        //获取验证码
        String code = captcha.text().toLowerCase();
        //将验证码存入缓存
        checkCodeCache.put(codeId, code);
        //获取验证码图片
        String base64 = captcha.toBase64();
        //返回验证码id、验证码图片base64
        return ResponseResult.success(new CheckCodeVo(codeId, base64));
    }

    @Override
    public boolean checkCode(String codeId, String code) {
        //获取缓存中的验证码
        String cacheCode = checkCodeCache.get(codeId);
        //比较验证码
        return cacheCode != null && cacheCode.equalsIgnoreCase(code);
    }
}
