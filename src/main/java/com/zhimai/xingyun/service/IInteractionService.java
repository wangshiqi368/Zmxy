package com.zhimai.xingyun.service;

import com.zhimai.xingyun.dto.InteractionCreateDTO;
import com.zhimai.xingyun.dto.InteractionDTO;

import java.util.List;

/**
 * 互动记录与待办工作流业务接口
 */
public interface IInteractionService {

    /**
     * 为联系人追加一条互动记录
     */
    void addInteraction(InteractionCreateDTO dto, Long userId);

    /**
     * 切换跟进状态 (已完成/未完成)
     */
    void toggleFollowUpStatus(Long interactionId, Long userId);

    /**
     * 获取用户近期（如：未完成且有日期的）待办事项列表
     */
    List<InteractionDTO> getUpcomingFollowUps(Long userId);
}
