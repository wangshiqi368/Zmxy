package com.zhimai.xingyun.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 新建互动记录时使用的数据传输对象 (DTO)
 */
@Data
public class InteractionCreateDTO {

    private Long contactId;

    /**
     * 互动类型 (如: MEETING, PHONE, DINNER, OTHER)
     */
    private String interactionType;

    /**
     * 沟通纪要
     */
    private String summary;

    /**
     * 互动发生的实际时间
     */
    private LocalDateTime interactionTime;

    /**
     * 可选：下次跟进计划日期
     */
    private LocalDate nextFollowUpDate;

    public Long getContactId() { return contactId; }
    public void setContactId(Long contactId) { this.contactId = contactId; }
}
