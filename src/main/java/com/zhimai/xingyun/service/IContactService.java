package com.zhimai.xingyun.service;

import com.zhimai.xingyun.dto.ContactCreateDTO;

import com.zhimai.xingyun.dto.ContactListItemDTO;

import java.util.List;
import java.util.Map;

/**
 * 联系人模块业务逻辑接口
 *
 * @author ZMXY
 * @since 2026-05-21
 */
public interface IContactService {

    /**
     * 创建一个新的联系人及其关联的标签
     * @param contactCreateDTO 联系人数据
     * @param userId 当前操作的用户ID
     */
    void createContact(ContactCreateDTO contactCreateDTO, Long userId);

    /**
     * 获取联系人详情，包括基础信息、标签和互动时间线
     * @param contactId 联系人ID
     * @param userId 当前操作的用户ID
     * @return 聚合后的视图对象
     */
    ContactDetailVO getContactDetail(Long contactId, Long userId);

    /**
     * 获取按首字母分组的联系人列表
     * @param userId 当前操作的用户ID
     * @return 分组后的联系人列表
     */
    Map<String, List<ContactListItemDTO>> getContactListGrouped(Long userId);
}
