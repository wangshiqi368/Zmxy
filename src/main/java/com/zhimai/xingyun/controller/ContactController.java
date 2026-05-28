package com.zhimai.xingyun.controller;

import com.zhimai.xingyun.dto.ContactCreateDTO;
import com.zhimai.xingyun.dto.ContactDetailVO;
import com.zhimai.xingyun.dto.ContactListItemDTO;
import com.zhimai.xingyun.dto.ContactUpdateDTO;
import com.zhimai.xingyun.service.IContactService;
import com.zhimai.xingyun.util.UserContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
        contactService.createContact(contactCreateDTO, UserContext.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateContact(@PathVariable("id") Long contactId, @RequestBody ContactUpdateDTO contactUpdateDTO) {
        contactService.updateContact(contactId, contactUpdateDTO, UserContext.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactListItemDTO>> searchContacts(@RequestParam("keyword") String keyword) {
        List<ContactListItemDTO> searchResults = contactService.searchContacts(keyword, UserContext.getUserId());
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ContactListItemDTO>> getRecentContacts() {
        List<ContactListItemDTO> recentContacts = contactService.getRecentContacts(UserContext.getUserId());
        return ResponseEntity.ok(recentContacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDetailVO> getContactDetail(@PathVariable("id") Long contactId) {
        ContactDetailVO contactDetail = contactService.getContactDetail(contactId, UserContext.getUserId());
        return ResponseEntity.ok(contactDetail);
    }
}
