package com.zhimai.xingyun.util;

/**
 * 线程上下文工具，用于在拦截器与 Service 之间传递当前登录用户 ID
 */
public class UserContext {
    private static final ThreadLocal<Long> userHolder = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        userHolder.set(userId);
    }

    public static Long getUserId() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}
