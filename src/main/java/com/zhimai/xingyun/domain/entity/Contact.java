package com.zhimai.xingyun.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 联系人基础信息表 实体类
 *
 * @author ZMXY
 * @since 2026-05-21
 */
@Data
@Accessors(chain = true)
@TableName("contacts")
public class Contact {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("name")
    private String name;

    @TableField("pinyin_name")
    private String pinyinName;

    @TableField("company")
    private String company;

    @TableField("title")
    private String title;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("wechat_id")
    private String wechatId;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("remark")
    private String remark;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPinyinName() { return pinyinName; }
    public void setPinyinName(String pinyinName) { this.pinyinName = pinyinName; }
}
