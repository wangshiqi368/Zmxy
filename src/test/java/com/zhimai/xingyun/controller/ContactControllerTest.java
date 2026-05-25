package com.zhimai.xingyun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhimai.xingyun.config.JwtInterceptor;
import com.zhimai.xingyun.domain.entity.Contact;
import com.zhimai.xingyun.dto.ContactCreateDTO;
import com.zhimai.xingyun.dto.ContactDetailVO;
import com.zhimai.xingyun.dto.ContactListItemDTO;
import com.zhimai.xingyun.service.IContactService;
import com.zhimai.xingyun.util.UserContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IContactService contactService;

    @MockBean
    private JwtInterceptor jwtInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    private MockedStatic<UserContext> mockedUserContext;

    @BeforeEach
    void setUp() throws Exception {
        mockedUserContext = mockStatic(UserContext.class);
        mockedUserContext.when(UserContext::getUserId).thenReturn(1L);
        // 绕过拦截器
        when(jwtInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        mockedUserContext.close();
    }

    @Test
    @DisplayName("创建联系人成功 - 返回201")
    void createContactSuccess() throws Exception {
        ContactCreateDTO dto = new ContactCreateDTO();
        dto.setName("张三");

        doNothing().when(contactService).createContact(any(ContactCreateDTO.class), eq(1L));

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("获取联系人列表成功 - 返回分组数据")
    void getContactListSuccess() throws Exception {
        Map<String, List<ContactListItemDTO>> groupedContacts = new HashMap<>();
        List<ContactListItemDTO> list = new ArrayList<>();
        ContactListItemDTO item = new ContactListItemDTO();
        item.setId(1L);
        item.setName("张三");
        list.add(item);
        groupedContacts.put("Z", list);

        when(contactService.getContactListGrouped(1L)).thenReturn(groupedContacts);

        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Z[0].name").value("张三"));
    }

    @Test
    @DisplayName("获取联系人详情成功")
    void getContactDetailSuccess() throws Exception {
        ContactDetailVO vo = new ContactDetailVO();
        Contact baseInfo = new Contact();
        baseInfo.setId(1L);
        baseInfo.setName("张三");
        vo.setBaseInfo(baseInfo);
        vo.setTimeline(new ArrayList<>());
        vo.setTags(new ArrayList<>());

        when(contactService.getContactDetail(1L, 1L)).thenReturn(vo);

        mockMvc.perform(get("/api/contacts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseInfo.name").value("张三"));
    }

    @Test
    @DisplayName("删除联系人成功 - 返回204")
    void deleteContactSuccess() throws Exception {
        doNothing().when(contactService).deleteContact(1L, 1L);

        mockMvc.perform(delete("/api/contacts/1"))
                .andExpect(status().isNoContent());
    }
}
