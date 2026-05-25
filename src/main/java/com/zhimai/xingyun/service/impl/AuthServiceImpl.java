package com.zhimai.xingyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhimai.xingyun.domain.entity.User;
import com.zhimai.xingyun.dto.AuthRequestDTO;
import com.zhimai.xingyun.dto.AuthResponseDTO;
import com.zhimai.xingyun.exception.UnauthorizedException;
import com.zhimai.xingyun.mapper.UserMapper;
import com.zhimai.xingyun.service.IAuthService;
import com.zhimai.xingyun.util.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 身份认证服务实现类
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(UserMapper userMapper, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(AuthRequestDTO dto) {
        // 1. 校验用户名是否已存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 创建用户并对密码进行加密
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(1); // 默认启用

        userMapper.insert(user);
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        // 1. 根据用户名查找用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        User user = userMapper.selectOne(wrapper);

        // 2. 校验密码
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("用户名或密码错误");
        }

        // 3. 生成 Token
        String token = jwtUtils.createToken(user.getId(), user.getUsername());

        // 4. 组装结果
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setToken(token);
        responseDTO.setUsername(user.getUsername());
        return responseDTO;
    }
}
