package com.backend.controller;

import com.backend.common.result.PageResult;
import com.backend.common.result.Result;
import com.backend.entity.User;
import com.backend.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户管理控制器
 * 
 * @author backend
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    public Result<PageResult<User>> getUserPage(
        @RequestParam(defaultValue = "1") Long current,
        @RequestParam(defaultValue = "10") Long size,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Integer status) {
        
        try {
            IPage<User> page = userService.getUserPage(current, size, username, email, status);
            PageResult<User> pageResult = PageResult.of(
                page.getRecords(), 
                page.getTotal(), 
                page.getCurrent(), 
                page.getSize()
            );
            return Result.success("查询成功", pageResult);
        } catch (Exception e) {
            log.error("分页查询用户列表失败", e);
            return Result.error("查询失败");
        }
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable @NotNull Long id) {
        try {
            User user = userService.getById(id);
            return Result.success("查询成功", user);
        } catch (Exception e) {
            log.error("根据ID查询用户失败", e);
            return Result.error("查询失败");
        }
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    public Result<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            return Result.success("查询成功", user);
        } catch (Exception e) {
            log.error("根据用户名查询用户失败", e);
            return Result.error("查询失败");
        }
    }

    /**
     * 创建用户
     */
    @PostMapping
    public Result<User> createUser(@RequestBody @Valid User user) {
        try {
            User createdUser = userService.createUser(user);
            return Result.success("用户创建成功", createdUser);
        } catch (Exception e) {
            log.error("创建用户失败", e);
            return Result.error("创建失败");
        }
    }

    /**
     * 更新用户
     */
    @PutMapping
    public Result<User> updateUser(@RequestBody @Valid User user) {
        try {
            User updatedUser = userService.updateUser(user);
            return Result.success("用户更新成功", updatedUser);
        } catch (Exception e) {
            log.error("更新用户失败", e);
            return Result.error("更新失败");
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable @NotNull Long id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return Result.<Void>success("用户删除成功", null);
            } else {
                return Result.<Void>error("用户删除失败");
            }
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return Result.<Void>error("删除失败");
        }
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batch")
    public Result<Void> deleteUsers(@RequestBody List<Long> ids) {
        try {
            boolean deleted = userService.deleteUsers(ids);
            if (deleted) {
                return Result.<Void>success("用户批量删除成功", null);
            } else {
                return Result.<Void>error("用户批量删除失败");
            }
        } catch (Exception e) {
            log.error("批量删除用户失败", e);
            return Result.<Void>error("批量删除失败");
        }
    }

    /**
     * 更新用户状态
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateUserStatus(
        @PathVariable @NotNull Long id,
        @RequestParam @NotNull Integer status) {
        try {
            boolean updated = userService.updateUserStatus(id, status);
            if (updated) {
                return Result.<Void>success("用户状态更新成功", null);
            } else {
                return Result.<Void>error("用户状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新用户状态失败", e);
            return Result.<Void>error("状态更新失败");
        }
    }

    /**
     * 重置用户密码
     */
    @PutMapping("/{id}/password")
    public Result<Void> resetPassword(
        @PathVariable @NotNull Long id,
        @RequestParam @NotNull String newPassword) {
        try {
            boolean reset = userService.resetPassword(id, newPassword);
            if (reset) {
                return Result.<Void>success("密码重置成功", null);
            } else {
                return Result.<Void>error("密码重置失败");
            }
        } catch (Exception e) {
            log.error("重置用户密码失败", e);
            return Result.<Void>error("密码重置失败");
        }
    }

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/exists/username")
    public Result<Boolean> existsByUsername(@RequestParam String username) {
        try {
            boolean exists = userService.existsByUsername(username);
            return Result.success("查询成功", exists);
        } catch (Exception e) {
            log.error("检查用户名是否存在失败", e);
            return Result.error("查询失败");
        }
    }

    /**
     * 检查邮箱是否存在
     */
    @GetMapping("/exists/email")
    public Result<Boolean> existsByEmail(@RequestParam String email) {
        try {
            boolean exists = userService.existsByEmail(email);
            return Result.success("查询成功", exists);
        } catch (Exception e) {
            log.error("检查邮箱是否存在失败", e);
            return Result.error("查询失败");
        }
    }

    /**
     * 检查手机号是否存在
     */
    @GetMapping("/exists/phone")
    public Result<Boolean> existsByPhone(@RequestParam String phone) {
        try {
            boolean exists = userService.existsByPhone(phone);
            return Result.success("查询成功", exists);
        } catch (Exception e) {
            log.error("检查手机号是否存在失败", e);
            return Result.error("查询失败");
        }
    }

    /**
     * 获取用户统计信息
     */
    @GetMapping("/statistics")
    public Result<UserService.UserStatistics> getUserStatistics() {
        try {
            UserService.UserStatistics statistics = userService.getUserStatistics();
            return Result.success("查询成功", statistics);
        } catch (Exception e) {
            log.error("获取用户统计信息失败", e);
            return Result.error("查询失败");
        }
    }
}