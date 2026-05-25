package com.zhimai.xingyun.config;

import com.zhimai.xingyun.exception.UnauthorizedException;
import com.zhimai.xingyun.util.JwtUtils;
import com.zhimai.xingyun.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 身份验证拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    public JwtInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从 Header 中获取 Token
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnauthorizedException("未认证，请先登录");
        }

        token = token.substring(7); // 去掉 "Bearer " 前缀

        try {
            // 2. 解析 Token 并获取用户 ID
            Long userId = jwtUtils.getUserId(token);
            
            // 3. 将用户 ID 存入 ThreadLocal，方便后续业务逻辑获取
            UserContext.setUserId(userId);
            return true;
        } catch (Exception e) {
            throw new UnauthorizedException("登录已过期或 Token 无效");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求结束后清除线程变量，防止内存泄漏
        UserContext.clear();
    }
}
