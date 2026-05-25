package com.zhimai.xingyun.service;

import com.zhimai.xingyun.dto.ContactCreateDTO;
import com.zhimai.xingyun.dto.ContactDetailVO;
import com.zhimai.xingyun.dto.ContactListItemDTO;
import com.zhimai.xingyun.dto.ContactUpdateDTO;

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
     * 更新联系人信息及关联标签
     * @param contactId 联系人ID
     * @param contactUpdateDTO 更新的数据
     * @param userId 当前操作的用户ID
     */
    void updateContact(Long contactId, ContactUpdateDTO contactUpdateDTO, Long userId);

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

    /**
     * 全局模糊搜索联系人
     * @param keyword 关键词 (姓名、公司、拼音)
     * @param userId 当前操作的用户ID
     * @return 匹配的联系人列表
     */
    List<ContactListItemDTO> searchContacts(String keyword, Long userId);

    /**
     * 删除联系人
     * @param contactId 联系人ID
     * @param userId 当前操作的用户ID
     */
    void deleteContact(Long contactId, Long userId);
}
