package com.group_8.filter;


import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.group_8.domain.ResponseResult;
import com.group_8.domain.constant.RedisConstant;
import com.group_8.domain.entity.LoginUser;
import com.group_8.domain.enums.HttpCodeEnum;

import com.group_8.util.JwtUtil;
import com.group_8.util.WebUtils;
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