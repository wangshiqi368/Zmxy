package com.zhimai.xingyun.service;

import com.zhimai.xingyun.dto.AuthRequestDTO;
import com.zhimai.xingyun.dto.AuthResponseDTO;

/**
 * 身份认证服务接口
 */
public interface IAuthService {
    /**
     * 用户注册
     */
    void register(AuthRequestDTO dto);

    /**
     * 用户登录并返回 Token
     */
    AuthResponseDTO login(AuthRequestDTO dto);
}
