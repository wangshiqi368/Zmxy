package com.zhimai.xingyun.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 互动记录信息的DTO
 */
@Data
public class InteractionDTO {
    private Long id;
    private String interactionType;
    private String summary;
    private LocalDateTime interactionTime;
    private LocalDate nextFollowUpDate;
    private Integer isFollowUpCompleted;
}
