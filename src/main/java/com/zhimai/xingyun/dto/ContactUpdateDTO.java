package com.zhimai.xingyun.dto;

import lombok.Data;
import java.util.List;

/**
 * 修改联系人时使用的数据传输对象 (DTO)
 */
@Data
public class ContactUpdateDTO {

    private String name;
    
    private String company;
    
    private String title;
    
    private String phone;
    
    private String wechatId;

    private String email;
    
    private String remark;
    
    private List<Long> tagIds; 

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Long> getTagIds() { return tagIds; }
    public void setTagIds(List<Long> tagIds) { this.tagIds = tagIds; }
}
