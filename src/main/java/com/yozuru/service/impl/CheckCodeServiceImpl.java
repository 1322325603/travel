package com.yozuru.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.constant.RedisConstant;
import com.yozuru.domain.vo.CheckCodeVo;
import com.yozuru.service.CheckCodeService;
import org.springframework.stereotype.Service;

/**
 * @author Yozuru
 */
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
