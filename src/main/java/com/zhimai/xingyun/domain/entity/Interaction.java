package com.zhimai.xingyun.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 互动记录与待办工作流表 实体类
 *
 * @author ZMXY
 * @since 2026-05-21
 */
@Data
@Accessors(chain = true)
@TableName("interactions")
public class Interaction {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("contact_id")
    private Long contactId;

    @TableField("interaction_type")
    private String interactionType;

    @TableField("summary")
    private String summary;

    @TableField("interaction_time")
    private LocalDateTime interactionTime;

    @TableField("next_follow_up_date")
    private LocalDate nextFollowUpDate;

    @TableField("is_follow_up_completed")
    private Integer isFollowUpCompleted;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    public Long getContactId() { return contactId; }
    public void setContactId(Long contactId) { this.contactId = contactId; }
    public Integer getIsFollowUpCompleted() { return isFollowUpCompleted; }
    public void setIsFollowUpCompleted(Integer isFollowUpCompleted) { this.isFollowUpCompleted = isFollowUpCompleted; }
}
