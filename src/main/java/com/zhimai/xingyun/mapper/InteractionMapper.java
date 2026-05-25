package com.zhimai.xingyun.mapper;

import com.zhimai.xingyun.domain.entity.Interaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhimai.xingyun.dto.InteractionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 互动记录与待办工作流表 Mapper 接口
 * </p>
 *
 * @author ZMXY
 * @since 2026-05-21
 */
public interface InteractionMapper extends BaseMapper<Interaction> {

    /**
     * 获取用户近期未完成的待办事项 (按日期升序)
     */
    List<InteractionDTO> findUpcomingFollowUpsByUserId(@Param("userId") Long userId);
}
