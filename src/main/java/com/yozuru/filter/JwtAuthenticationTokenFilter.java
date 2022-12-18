package com.yozuru.filter;


import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.constant.RedisConstant;
import com.yozuru.domain.entity.LoginUser;
import com.yozuru.domain.enums.HttpCodeEnum;

import com.yozuru.util.JwtUtil;
import com.yozuru.util.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Yozuru
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @CreateCache(area = "userLogin", name = RedisConstant.USER_KEY_PREFIX,cacheType = CacheType.REMOTE)
    private Cache<Integer, LoginUser> userDetailCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=request.getHeader("token");
        if (!Strings.hasText(token)){
            //没有token就直接放行，可能接口不需要登录，也可能是尝试越权访问。
            filterChain.doFilter(request,response);
            return;
        }
        Claims claims=null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //token 超时 、token非法
            ResponseResult<Object> result = ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        Integer userid = Integer.parseInt(claims.getSubject());
        LoginUser loginUser = userDetailCache.get(userid);

        if(Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult<Object> result = ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}