package com.zhimai.xingyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhimai.xingyun.domain.entity.Contact;
import com.zhimai.xingyun.domain.entity.Interaction;
import com.zhimai.xingyun.dto.ContactCreateDTO;
import com.zhimai.xingyun.dto.ContactDetailVO;
import com.zhimai.xingyun.dto.ContactListItemDTO;
import com.zhimai.xingyun.dto.ContactUpdateDTO;
import com.zhimai.xingyun.dto.InteractionDTO;
import com.zhimai.xingyun.dto.TagDTO;
import com.zhimai.xingyun.exception.ResourceNotFoundException;
import com.zhimai.xingyun.mapper.ContactMapper;
import com.zhimai.xingyun.mapper.ContactTagMapper;
import com.zhimai.xingyun.mapper.InteractionMapper;
import com.zhimai.xingyun.mapper.TagMapper;
import com.zhimai.xingyun.service.IContactService;
import com.zhimai.xingyun.util.PinyinUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 联系人模块业务逻辑实现
 *
 * @author ZMXY
 * @since 2026-05-21
 */
@Service
public class ContactServiceImpl implements IContactService {

    private final ContactMapper contactMapper;
    private final ContactTagMapper contactTagMapper;
    private final TagMapper tagMapper;
    private final InteractionMapper interactionMapper;

    public ContactServiceImpl(ContactMapper contactMapper, ContactTagMapper contactTagMapper, TagMapper tagMapper, InteractionMapper interactionMapper) {
        this.contactMapper = contactMapper;
        this.contactTagMapper = contactTagMapper;
        this.tagMapper = tagMapper;
        this.interactionMapper = interactionMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createContact(ContactCreateDTO dto, Long userId) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(dto, contact);
        contact.setUserId(userId);
        contact.setPinyinName(PinyinUtils.getPinyin(dto.getName()));

        contactMapper.insert(contact);
        Long newContactId = contact.getId();

        if (!CollectionUtils.isEmpty(dto.getTagIds())) {
            contactTagMapper.batchInsertRelations(newContactId, dto.getTagIds());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContact(Long contactId, ContactUpdateDTO dto, Long userId) {
        // 1. 校验权限与存在性
        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", contactId).eq("user_id", userId);
        Contact existingContact = contactMapper.selectOne(queryWrapper);
        if (existingContact == null) {
            throw new ResourceNotFoundException("联系人不存在或无权访问");
        }

        // 2. 更新基础信息
        BeanUtils.copyProperties(dto, existingContact);
        // 如果姓名发生变化，重新生成拼音
        existingContact.setPinyinName(PinyinUtils.getPinyin(dto.getName()));
        contactMapper.updateById(existingContact);

        // 3. 更新标签关联 (先删后增)
        QueryWrapper<com.zhimai.xingyun.domain.entity.ContactTag> tagRelationWrapper = new QueryWrapper<>();
        tagRelationWrapper.eq("contact_id", contactId);
        contactTagMapper.delete(tagRelationWrapper);

        if (!CollectionUtils.isEmpty(dto.getTagIds())) {
            contactTagMapper.batchInsertRelations(contactId, dto.getTagIds());
        }
    }

    @Override
    public ContactDetailVO getContactDetail(Long contactId, Long userId) {
        // 1. 获取基础信息 (并校验权限)
        QueryWrapper<Contact> contactWrapper = new QueryWrapper<>();
        contactWrapper.eq("id", contactId).eq("user_id", userId);
        Contact contact = contactMapper.selectOne(contactWrapper);

        if (contact == null) {
            throw new ResourceNotFoundException("联系人不存在或无权访问");
        }

        // 2. 获取该联系人绑定的所有标签
        List<TagDTO> tags = tagMapper.findTagsByContactId(contactId);

        // 3. 获取按时间倒序排列的互动记录
        QueryWrapper<Interaction> interactionWrapper = new QueryWrapper<>();
        interactionWrapper.eq("contact_id", contactId).orderByDesc("interaction_time");
        List<Interaction> interactions = interactionMapper.selectList(interactionWrapper);

        // 4. 将 Interaction 实体列表转换为 DTO 列表
        List<InteractionDTO> interactionDTOs = interactions.stream().map(interaction -> {
            InteractionDTO dto = new InteractionDTO();
            BeanUtils.copyProperties(interaction, dto);
            return dto;
        }).collect(Collectors.toList());

        // 5. 组装并返回聚合对象 VO (View Object)
        ContactDetailVO detailVO = new ContactDetailVO();
        detailVO.setBaseInfo(contact);
        detailVO.setTags(tags);
        detailVO.setTimeline(interactionDTOs);

        return detailVO;
    }

    @Override
    public Map<String, List<ContactListItemDTO>> getContactListGrouped(Long userId) {
        // 1. 获取该用户的所有联系人，并按拼音排序
        QueryWrapper<Contact> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByAsc("pinyin_name");
        List<Contact> contacts = contactMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(contacts)) {
            return Collections.emptyMap();
        }

        // 2. 按拼音首字母分组
        return contacts.stream()
                .collect(Collectors.groupingBy(
                        // 分组逻辑
                        contact -> {
                            String pinyin = contact.getPinyinName();
                            if (pinyin == null || pinyin.isEmpty()) {
                                return "#";
                            }
                            char firstChar = pinyin.toUpperCase().charAt(0);
                            if (firstChar >= 'A' && firstChar <= 'Z') {
                                return String.valueOf(firstChar);
                            }
                            return "#";
                        },
                        // 使用 LinkedHashMap 保证首字母顺序
                        LinkedHashMap::new,
                        // 下游收集器：将 Contact 映射为 ContactListItemDTO
                        Collectors.mapping(
                                contact -> {
                                    ContactListItemDTO dto = new ContactListItemDTO();
                                    BeanUtils.copyProperties(contact, dto);
                                    return dto;
                                },
                                Collectors.toList()
                        )
                ));
    }

    @Override
    public List<ContactListItemDTO> searchContacts(String keyword, Long userId) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }

        QueryWrapper<Contact> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .and(w -> w.like("name", keyword)
                        .or().like("company", keyword)
                        .or().like("pinyin_name", keyword));

        List<Contact> contacts = contactMapper.selectList(wrapper);

        return contacts.stream().map(contact -> {
            ContactListItemDTO dto = new ContactListItemDTO();
            BeanUtils.copyProperties(contact, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContact(Long contactId, Long userId) {
        // 1. 校验权限与存在性
        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", contactId).eq("user_id", userId);
        Contact existingContact = contactMapper.selectOne(queryWrapper);
        if (existingContact == null) {
            throw new ResourceNotFoundException("联系人不存在或无权访问");
        }

        // 2. 执行删除 (数据库已配置级联删除，所以只需删除主表记录)
        contactMapper.deleteById(contactId);
    }

    @Override
    public List<ContactListItemDTO> getRecentContacts(Long userId) {
        List<Contact> contacts = contactMapper.findRecentContacts(userId);
        return contacts.stream().map(contact -> {
            ContactListItemDTO dto = new ContactListItemDTO();
            BeanUtils.copyProperties(contact, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}

