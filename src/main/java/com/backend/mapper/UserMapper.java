package com.backend.mapper;

import com.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户Mapper接口
 * 
 * @author backend
 * @since 1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND deleted = 0")
    User findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     */
    @Select("SELECT * FROM sys_user WHERE email = #{email} AND deleted = 0")
    User findByEmail(@Param("email") String email);

    /**
     * 根据手机号查询用户
     */
    @Select("SELECT * FROM sys_user WHERE phone = #{phone} AND deleted = 0")
    User findByPhone(@Param("phone") String phone);

    /**
     * 查询正常状态的用户列表
     */
    @Select("SELECT * FROM sys_user WHERE status = 0 AND deleted = 0 ORDER BY create_time DESC")
    List<User> findActiveUsers();

    /**
     * 根据状态查询用户数量
     */
    @Select("SELECT COUNT(*) FROM sys_user WHERE status = #{status} AND deleted = 0")
    Long countByStatus(@Param("status") Integer status);
}