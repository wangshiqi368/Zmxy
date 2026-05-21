package com.zhimai.xingyun.service.impl;

import com.zhimai.xingyun.domain.entity.Contact;
import com.zhimai.xingyun.dto.ContactCreateDTO;
import com.zhimai.xingyun.mapper.ContactMapper;
import com.zhimai.xingyun.mapper.ContactTagMapper;
import com.zhimai.xingyun.service.IContactService;
import com.zhimai.xingyun.util.PinyinUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    public ContactServiceImpl(ContactMapper contactMapper, ContactTagMapper contactTagMapper) {
        this.contactMapper = contactMapper;
        this.contactTagMapper = contactTagMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createContact(ContactCreateDTO dto, Long userId) {
        // 1. 转换并保存联系人主信息
        Contact contact = new Contact();
        contact.setUserId(userId);
        contact.setName(dto.getName());
        contact.setCompany(dto.getCompany());
        contact.setTitle(dto.getTitle());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        contact.setWechatId(dto.getWechatId());
        contact.setRemark(dto.getRemark());

        // 2. 自动生成拼音
        contact.setPinyinName(PinyinUtils.getPinyin(dto.getName()));

        // 3. 插入主表
        // MyBatis-Plus 插入后，会自动将生成的主键ID回填到 contact 对象中
        contactMapper.insert(contact);
        Long newContactId = contact.getId();

        // 4. 处理多对多标签关联
        if (!CollectionUtils.isEmpty(dto.getTagIds())) {
            contactTagMapper.batchInsertRelations(newContactId, dto.getTagIds());
        }
    }
}

