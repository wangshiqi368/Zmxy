package com.zhimai.xingyun.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 联系人-标签多对多关联表 实体类
 *
 * @author ZMXY
 * @since 2026-05-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("contact_tags")
public class ContactTag {

    /**
     * 联系人ID
     */
    @TableField("contact_id")
    private Long contactId;

    /**
     * 标签ID
     */
    @TableField("tag_id")
    private Long tagId;
}
