package com.zhimai.xingyun.dto;

import lombok.Data;
import java.util.List;

/**
 * 新建联系人时使用的数据传输对象 (DTO)
 */
@Data
public class ContactCreateDTO {

    private String name;
    
    private String company;
    
    private String title;
    
    private String phone;
    
    private String wechatId;

    private String email;
    
    private String remark;
    
    // 关键点：接收前端选中的标签ID集合
    private List<Long> tagIds; 
}
