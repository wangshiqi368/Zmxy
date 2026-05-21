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

    /**
     * 所属用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 姓名拼音
     */
    @TableField("pinyin_name")
    private String pinyinName;

    /**
     * 公司/组织名称
     */
    @TableField("company")
    private String company;

    /**
     * 职位/职称
     */
    @TableField("title")
    private String title;

    /**
     * 电话号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 电子邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 微信号
     */
    @TableField("wechat_id")
    private String wechatId;

    /**
     * 联系人头像/名片图片地址
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 背景备注
     */
    @TableField("remark")
    private String remark;

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
