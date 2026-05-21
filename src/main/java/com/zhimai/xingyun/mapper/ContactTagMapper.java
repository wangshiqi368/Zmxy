package com.zhimai.xingyun.mapper;

import com.zhimai.xingyun.domain.entity.ContactTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 联系人-标签多对多关联表 Mapper 接口
 * </p>
 *
 * @author ZMXY
 * @since 2026-05-21
 */
public interface ContactTagMapper extends BaseMapper<ContactTag> {

    /**
     * 批量插入联系人与标签的关联关系
     * (此方法的 SQL 实现在对应的 XML 文件中)
     *
     * @param contactId 联系人ID
     * @param tagIds    标签ID列表
     * @return 插入的行数
     */
    int batchInsertRelations(@Param("contactId") Long contactId, @Param("tagIds") List<Long> tagIds);
}
