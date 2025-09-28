package com.backend.service.impl;

import com.backend.common.exception.BusinessException;
import com.backend.common.result.ResultCode;
import com.backend.common.utils.StringUtils;
import com.backend.entity.User;
import com.backend.mapper.UserMapper;
import com.backend.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类
 * 
 * @author backend
 * @since 1.0.0
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public IPage<User> getUserPage(Long current, Long size, String username, String email, Integer status) {
        Page<User> page = new Page<>(current, size);
        
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), User::getUsername, username)
                   .like(StringUtils.isNotBlank(email), User::getEmail, email)
                   .eq(status != null, User::getStatus, status)
                   .orderByDesc(User::getCreateTime);
        
        return this.page(page, queryWrapper);
    }

    @Override
    public User getUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return baseMapper.findByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return null;
        }
        return baseMapper.findByEmail(email);
    }

    @Override
    public User getUserByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        return baseMapper.findByPhone(phone);
    }

    @Override
    public User createUser(User user) {
        // 参数校验
        if (user == null) {
            throw BusinessException.of(ResultCode.BAD_REQUEST, "用户信息不能为空");
        }
        
        // 检查用户名是否已存在
        if (existsByUsername(user.getUsername())) {
            throw BusinessException.of(ResultCode.DATA_ALREADY_EXISTS, "用户名已存在");
        }
        
        // 检查邮箱是否已存在
        if (StringUtils.isNotBlank(user.getEmail()) && existsByEmail(user.getEmail())) {
            throw BusinessException.of(ResultCode.DATA_ALREADY_EXISTS, "邮箱已存在");
        }
        
        // 检查手机号是否已存在
        if (StringUtils.isNotBlank(user.getPhone()) && existsByPhone(user.getPhone())) {
            throw BusinessException.of(ResultCode.DATA_ALREADY_EXISTS, "手机号已存在");
        }
        
        // 设置默认值
        if (user.getStatus() == null) {
            user.setStatus(0);
        }
        
        // 保存用户
        boolean saved = this.save(user);
        if (!saved) {
            throw BusinessException.of(ResultCode.DATABASE_ERROR, "用户创建失败");
        }
        
        log.info("用户创建成功: {}", user.getUsername());
        return user;
    }

    @Override
    public User updateUser(User user) {
        // 参数校验
        if (user == null || user.getId() == null) {
            throw BusinessException.of(ResultCode.BAD_REQUEST, "用户信息不能为空");
        }
        
        // 检查用户是否存在
        User existingUser = this.getById(user.getId());
        if (existingUser == null) {
            throw BusinessException.of(ResultCode.DATA_NOT_FOUND, "用户不存在");
        }
        
        // 检查用户名是否被其他用户使用
        if (StringUtils.isNotBlank(user.getUsername()) && 
            !user.getUsername().equals(existingUser.getUsername()) && 
            existsByUsername(user.getUsername())) {
            throw BusinessException.of(ResultCode.DATA_ALREADY_EXISTS, "用户名已被其他用户使用");
        }
        
        // 检查邮箱是否被其他用户使用
        if (StringUtils.isNotBlank(user.getEmail()) && 
            !user.getEmail().equals(existingUser.getEmail()) && 
            existsByEmail(user.getEmail())) {
            throw BusinessException.of(ResultCode.DATA_ALREADY_EXISTS, "邮箱已被其他用户使用");
        }
        
        // 检查手机号是否被其他用户使用
        if (StringUtils.isNotBlank(user.getPhone()) && 
            !user.getPhone().equals(existingUser.getPhone()) && 
            existsByPhone(user.getPhone())) {
            throw BusinessException.of(ResultCode.DATA_ALREADY_EXISTS, "手机号已被其他用户使用");
        }
        
        // 更新用户
        boolean updated = this.updateById(user);
        if (!updated) {
            throw BusinessException.of(ResultCode.DATABASE_ERROR, "用户更新失败");
        }
        
        log.info("用户更新成功: {}", user.getUsername());
        return this.getById(user.getId());
    }

    @Override
    public boolean deleteUser(Long id) {
        if (id == null) {
            throw BusinessException.of(ResultCode.BAD_REQUEST, "用户ID不能为空");
        }
        
        User user = this.getById(id);
        if (user == null) {
            throw BusinessException.of(ResultCode.DATA_NOT_FOUND, "用户不存在");
        }
        
        boolean deleted = this.removeById(id);
        if (deleted) {
            log.info("用户删除成功: {}", user.getUsername());
        }
        
        return deleted;
    }

    @Override
    public boolean deleteUsers(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw BusinessException.of(ResultCode.BAD_REQUEST, "用户ID列表不能为空");
        }
        
        boolean deleted = this.removeByIds(ids);
        if (deleted) {
            log.info("批量删除用户成功，数量: {}", ids.size());
        }
        
        return deleted;
    }

    @Override
    public boolean updateUserStatus(Long id, Integer status) {
        if (id == null || status == null) {
            throw BusinessException.of(ResultCode.BAD_REQUEST, "用户ID和状态不能为空");
        }
        
        User user = this.getById(id);
        if (user == null) {
            throw BusinessException.of(ResultCode.DATA_NOT_FOUND, "用户不存在");
        }
        
        user.setStatus(status);
        boolean updated = this.updateById(user);
        
        if (updated) {
            log.info("用户状态更新成功: {} -> {}", user.getUsername(), status);
        }
        
        return updated;
    }

    @Override
    public boolean resetPassword(Long id, String newPassword) {
        if (id == null || StringUtils.isBlank(newPassword)) {
            throw BusinessException.of(ResultCode.BAD_REQUEST, "用户ID和新密码不能为空");
        }
        
        User user = this.getById(id);
        if (user == null) {
            throw BusinessException.of(ResultCode.DATA_NOT_FOUND, "用户不存在");
        }
        
        user.setPassword(newPassword);
        boolean updated = this.updateById(user);
        
        if (updated) {
            log.info("用户密码重置成功: {}", user.getUsername());
        }
        
        return updated;
    }

    @Override
    public boolean existsByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        return this.count(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return this.count(new LambdaQueryWrapper<User>().eq(User::getEmail, email)) > 0;
    }

    @Override
    public boolean existsByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        return this.count(new LambdaQueryWrapper<User>().eq(User::getPhone, phone)) > 0;
    }

    @Override
    public UserStatistics getUserStatistics() {
        Long totalUsers = this.count();
        Long activeUsers = baseMapper.countByStatus(0);
        Long disabledUsers = baseMapper.countByStatus(1);
        
        return new UserStatistics(totalUsers, activeUsers, disabledUsers);
    }
}