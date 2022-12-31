package com.group_8.util;

import com.group_8.domain.constant.SystemConstant;
import com.group_8.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Integer getUserId() {
        return getLoginUser().getUser().getId();
    }
    public static boolean isAdmin(){
        return SystemConstant.USER_ADMIN_ID.equals(getUserId());
    }
}