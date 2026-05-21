package com.zhimai.xingyun.service;

import com.zhimai.xingyun.dto.ContactCreateDTO;

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
}
