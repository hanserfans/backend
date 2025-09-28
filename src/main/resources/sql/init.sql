-- API底座项目数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `api_foundation` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `api_foundation`;

-- 创建用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态 (0: 正常, 1: 禁用)',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` VARCHAR(50) DEFAULT 'system' COMMENT '创建者',
    `update_by` VARCHAR(50) DEFAULT 'system' COMMENT '更新者',
    `deleted` INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识 (0: 未删除, 1: 已删除)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 插入初始用户数据
INSERT INTO `sys_user` (`username`, `password`, `email`, `phone`, `real_name`, `status`, `remark`) VALUES
('admin', '123456', 'admin@example.com', '13800138000', '系统管理员', 0, '系统默认管理员账户'),
('test', '123456', 'test@example.com', '13800138001', '测试用户', 0, '测试账户'),
('demo', '123456', 'demo@example.com', '13800138002', '演示用户', 0, '演示账户');

-- 创建角色表（扩展用）
CREATE TABLE IF NOT EXISTS `sys_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
    `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态 (0: 正常, 1: 禁用)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` VARCHAR(50) DEFAULT 'system' COMMENT '创建者',
    `update_by` VARCHAR(50) DEFAULT 'system' COMMENT '更新者',
    `deleted` INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识 (0: 未删除, 1: 已删除)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 插入初始角色数据
INSERT INTO `sys_role` (`role_name`, `role_code`, `description`, `status`) VALUES
('超级管理员', 'SUPER_ADMIN', '系统超级管理员，拥有所有权限', 0),
('管理员', 'ADMIN', '系统管理员，拥有大部分权限', 0),
('普通用户', 'USER', '普通用户，拥有基础权限', 0);

-- 创建用户角色关联表（扩展用）
CREATE TABLE IF NOT EXISTS `sys_user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by` VARCHAR(50) DEFAULT 'system' COMMENT '创建者',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 插入初始用户角色关联数据
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1), -- admin用户分配超级管理员角色
(2, 3), -- test用户分配普通用户角色
(3, 3); -- demo用户分配普通用户角色

-- 创建系统配置表（扩展用）
CREATE TABLE IF NOT EXISTS `sys_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_key` VARCHAR(100) NOT NULL COMMENT '配置键',
    `config_value` TEXT COMMENT '配置值',
    `config_type` VARCHAR(20) NOT NULL DEFAULT 'STRING' COMMENT '配置类型 (STRING, NUMBER, BOOLEAN, JSON)',
    `description` VARCHAR(200) DEFAULT NULL COMMENT '配置描述',
    `is_system` INT NOT NULL DEFAULT 0 COMMENT '是否系统配置 (0: 否, 1: 是)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` VARCHAR(50) DEFAULT 'system' COMMENT '创建者',
    `update_by` VARCHAR(50) DEFAULT 'system' COMMENT '更新者',
    `deleted` INT NOT NULL DEFAULT 0 COMMENT '逻辑删除标识 (0: 未删除, 1: 已删除)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`),
    KEY `idx_config_type` (`config_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- 插入初始系统配置
INSERT INTO `sys_config` (`config_key`, `config_value`, `config_type`, `description`, `is_system`) VALUES
('system.name', 'API Foundation', 'STRING', '系统名称', 1),
('system.version', '1.0.0', 'STRING', '系统版本', 1),
('system.description', '后端API底座项目', 'STRING', '系统描述', 1),
('user.default.password', '123456', 'STRING', '用户默认密码', 1),
('user.password.min.length', '6', 'NUMBER', '用户密码最小长度', 1),
('user.session.timeout', '30', 'NUMBER', '用户会话超时时间（分钟）', 1);

-- 创建操作日志表（扩展用）
CREATE TABLE IF NOT EXISTS `sys_operation_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT DEFAULT NULL COMMENT '操作用户ID',
    `username` VARCHAR(50) DEFAULT NULL COMMENT '操作用户名',
    `operation` VARCHAR(100) NOT NULL COMMENT '操作类型',
    `method` VARCHAR(200) NOT NULL COMMENT '请求方法',
    `params` TEXT COMMENT '请求参数',
    `result` TEXT COMMENT '返回结果',
    `ip` VARCHAR(50) DEFAULT NULL COMMENT 'IP地址',
    `location` VARCHAR(200) DEFAULT NULL COMMENT '操作地点',
    `user_agent` VARCHAR(500) DEFAULT NULL COMMENT '用户代理',
    `execute_time` BIGINT DEFAULT NULL COMMENT '执行时间（毫秒）',
    `status` INT NOT NULL DEFAULT 0 COMMENT '操作状态 (0: 成功, 1: 失败)',
    `error_msg` TEXT COMMENT '错误信息',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_username` (`username`),
    KEY `idx_operation` (`operation`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';