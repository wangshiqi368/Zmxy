package com.zhimai.xingyun.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 人脉标签字典表 实体类
 *
 * @author ZMXY
 * @since 2026-05-21
 */
@Data
@Accessors(chain = true)
@TableName("tags")
public class Tag {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建该标签的用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 标签名称
     */
    @TableField("name")
    private String name;

    /**
     * 十六进制颜色代码
     */
    @TableField("color_code")
    private String colorCode;

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
