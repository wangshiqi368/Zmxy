package com.zhimai.xingyun.controller;

import com.zhimai.xingyun.dto.ContactCreateDTO;
import com.zhimai.xingyun.service.IContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 联系人模块的API端点
 *
 * @author ZMXY
 * @since 2026-05-21
 */
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final IContactService contactService;

    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Void> createContact(@RequestBody ContactCreateDTO contactCreateDTO) {
        // TODO: 在 Phase 4 (安全认证) 中，此 ID 应从 Security Context 获取
        long currentUserId = 1L;

        contactService.createContact(contactCreateDTO, currentUserId);
        
        // 返回 201 Created 状态码，表示资源创建成功
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
