package com.zhimai.xingyun.dto;

import lombok.Data;

/**
 * 联系人列表项的DTO
 */
@Data
public class ContactListItemDTO {
    private Long id;
    private String name;
    private String company;
    private String title;
    private String avatarUrl;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
