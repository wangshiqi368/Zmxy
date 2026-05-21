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

    /**
     * 关联的联系人ID
     */
    @TableField("contact_id")
    private Long contactId;

    /**
     * 互动类型（如：MEETING/会议、PHONE/电话）
     */
    @TableField("interaction_type")
    private String interactionType;

    /**
     * 本次互动的详细沟通纪要
     */
    @TableField("summary")
    private String summary;

    /**
     * 本次互动发生的实际时间
     */
    @TableField("interaction_time")
    private LocalDateTime interactionTime;

    /**
     * 下一次计划跟进日期
     */
    @TableField("next_follow_up_date")
    private LocalDate nextFollowUpDate;

    /**
     * 跟进任务是否已完成 (0:未完成, 1:已完成)
     */
    @TableField("is_follow_up_completed")
    private Integer isFollowUpCompleted;

    /**
     * 录入时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
