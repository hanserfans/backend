package com.backend.service;

import com.backend.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户服务接口
 * 
 * @author backend
 * @since 1.0.0
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询用户列表
     */
    IPage<User> getUserPage(Long current, Long size, String username, String email, Integer status);

    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);

    /**
     * 根据邮箱查询用户
     */
    User getUserByEmail(String email);

    /**
     * 根据手机号查询用户
     */
    User getUserByPhone(String phone);

    /**
     * 创建用户
     */
    User createUser(User user);

    /**
     * 更新用户信息
     */
    User updateUser(User user);

    /**
     * 删除用户
     */
    boolean deleteUser(Long id);

    /**
     * 批量删除用户
     */
    boolean deleteUsers(List<Long> ids);

    /**
     * 启用/禁用用户
     */
    boolean updateUserStatus(Long id, Integer status);

    /**
     * 重置用户密码
     */
    boolean resetPassword(Long id, String newPassword);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 检查手机号是否存在
     */
    boolean existsByPhone(String phone);

    /**
     * 获取用户统计信息
     */
    UserStatistics getUserStatistics();

    /**
     * 用户统计信息内部类
     */
    class UserStatistics {
        private Long totalUsers;
        private Long activeUsers;
        private Long disabledUsers;

        // 构造函数、getter和setter
        public UserStatistics(Long totalUsers, Long activeUsers, Long disabledUsers) {
            this.totalUsers = totalUsers;
            this.activeUsers = activeUsers;
            this.disabledUsers = disabledUsers;
        }

        public Long getTotalUsers() { return totalUsers; }
        public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }

        public Long getActiveUsers() { return activeUsers; }
        public void setActiveUsers(Long activeUsers) { this.activeUsers = activeUsers; }

        public Long getDisabledUsers() { return disabledUsers; }
        public void setDisabledUsers(Long disabledUsers) { this.disabledUsers = disabledUsers; }
    }
}