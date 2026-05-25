package com.zhimai.xingyun.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhimai.xingyun.dto.AuthRequestDTO;
import com.zhimai.xingyun.dto.AuthResponseDTO;
import com.zhimai.xingyun.service.IAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("用户注册成功 - 返回200")
    void registerSuccess() throws Exception {
        AuthRequestDTO dto = new AuthRequestDTO();
        dto.setUsername("testuser");
        dto.setPassword("password123");

        doNothing().when(authService).register(any(AuthRequestDTO.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("注册成功"));
    }

    @Test
    @DisplayName("用户登录成功 - 返回Token")
    void loginSuccess() throws Exception {
        AuthRequestDTO dto = new AuthRequestDTO();
        dto.setUsername("testuser");
        dto.setPassword("password123");

        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setToken("mock-jwt-token");

        when(authService.login(any(AuthRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }
}
