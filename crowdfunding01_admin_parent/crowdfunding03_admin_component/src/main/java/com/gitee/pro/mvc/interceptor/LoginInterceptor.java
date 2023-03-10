package com.gitee.pro.mvc.interceptor;

import com.gitee.pro.entity.Admin;
import com.gitee.pro.exception.AccessForbiddenException;
import com.gitee.pro.util.CrowdConstant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 通过 request 对象获取 Session 对象
        HttpSession session = request.getSession();
        // 尝试从 Session 域中获取 Admin 对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        // 判断 admin 对象是否为空
        if (admin == null) {
            // 抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }
        // 如果 Admin 对象不为 null 则返回 true 放行
        return true;

    }

}
