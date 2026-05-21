package com.zhimai.xingyun.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户账户表 实体类
 *
 * @author ZMXY
 * @since 2026-05-21
 */
@Data
@Accessors(chain = true)
@TableName("users")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户名
     */
    @TableField("username")
    private String username;

    /**
     * 加密后的密码哈希
     */
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 用户电子邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 用户头像地址
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 账号状态 (0:禁用, 1:启用)
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
