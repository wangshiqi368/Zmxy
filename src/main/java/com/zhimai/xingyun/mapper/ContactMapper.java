package com.zhimai.xingyun.mapper;

import com.zhimai.xingyun.domain.entity.Contact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 联系人基础信息表 Mapper 接口
 * </p>
 *
 * @author ZMXY
 * @since 2026-05-21
 */
public interface ContactMapper extends BaseMapper<Contact> {

    /**
     * 获取最近30天内有互动记录的联系人
     */
    List<Contact> findRecentContacts(@Param("userId") Long userId);
}
