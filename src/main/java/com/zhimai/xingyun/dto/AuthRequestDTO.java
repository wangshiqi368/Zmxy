package com.zhimai.xingyun.dto;

import lombok.Data;

/**
 * 登录/注册请求对象
 */
@Data
public class AuthRequestDTO {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
