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
}
