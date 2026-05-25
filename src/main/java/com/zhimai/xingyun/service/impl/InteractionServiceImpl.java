package com.zhimai.xingyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhimai.xingyun.domain.entity.Contact;
import com.zhimai.xingyun.domain.entity.Interaction;
import com.zhimai.xingyun.dto.InteractionCreateDTO;
import com.zhimai.xingyun.dto.InteractionDTO;
import com.zhimai.xingyun.exception.ResourceNotFoundException;
import com.zhimai.xingyun.mapper.ContactMapper;
import com.zhimai.xingyun.mapper.InteractionMapper;
import com.zhimai.xingyun.service.IInteractionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 互动模块业务实现
 */
@Service
public class InteractionServiceImpl implements IInteractionService {

    private final InteractionMapper interactionMapper;
    private final ContactMapper contactMapper;

    public InteractionServiceImpl(InteractionMapper interactionMapper, ContactMapper contactMapper) {
        this.interactionMapper = interactionMapper;
        this.contactMapper = contactMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInteraction(InteractionCreateDTO dto, Long userId) {
        // 1. 权限校验：该联系人是否属于当前用户
        Contact contact = contactMapper.selectById(dto.getContactId());
        if (contact == null || !contact.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("联系人不存在或无权操作");
        }

        // 2. 构造实体并保存
        Interaction interaction = new Interaction();
        BeanUtils.copyProperties(dto, interaction);
        // 默认设置为未完成 (如果设置了下次跟进日期)
        interaction.setIsFollowUpCompleted(0);
        
        interactionMapper.insert(interaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleFollowUpStatus(Long interactionId, Long userId) {
        // 1. 获取互动记录
        Interaction interaction = interactionMapper.selectById(interactionId);
        if (interaction == null) {
            throw new ResourceNotFoundException("记录不存在");
        }

        // 2. 校验权限 (通过联系人)
        Contact contact = contactMapper.selectById(interaction.getContactId());
        if (contact == null || !contact.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("无权操作此记录");
        }

        // 3. 状态翻转 (0 -> 1, 1 -> 0)
        Integer currentStatus = interaction.getIsFollowUpCompleted();
        interaction.setIsFollowUpCompleted(currentStatus == 1 ? 0 : 1);
        
        interactionMapper.updateById(interaction);
    }

    @Override
    public List<InteractionDTO> getUpcomingFollowUps(Long userId) {
        // 这里为了简单，先通过联表逻辑获取
        // MVP 阶段可以直接用 MyBatis-Plus 的复杂查询或者自定义 Mapper SQL
        // 我们在 InteractionMapper 中增加一个自定义方法
        return interactionMapper.findUpcomingFollowUpsByUserId(userId);
    }
}
