package com.zhimai.xingyun.dto;

import com.zhimai.xingyun.domain.entity.Contact;
import lombok.Data;
import java.util.List;

/**
 * 联系人详情页的聚合视图对象 (VO)
 */
@Data
public class ContactDetailVO {
    private Contact baseInfo;
    private List<TagDTO> tags;
    private List<InteractionDTO> timeline;
}
