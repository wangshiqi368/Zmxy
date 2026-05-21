package com.zhimai.xingyun.mapper;

import com.zhimai.xingyun.domain.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhimai.xingyun.dto.TagDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 人脉标签字典表 Mapper 接口
 * </p>
 *
 * @author ZMXY
 * @since 2026-05-21
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据联系人ID查询其关联的所有标签
     * @param contactId 联系人ID
     * @return 标签DTO列表
     */
    List<TagDTO> findTagsByContactId(@Param("contactId") Long contactId);
}
