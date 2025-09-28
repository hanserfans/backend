package com.backend.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 通用响应结果封装类
 * 
 * @author backend
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    
    /**
     * 响应码
     */
    private Integer code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    /**
     * 成功响应
     */
    public static <T> Result<T> success() {
        return success(null);
    }
    
    /**
     * 成功响应带数据
     */
    public static <T> Result<T> success(T data) {
        return success("操作成功", data);
    }
    
    /**
     * 成功响应带消息和数据
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
    
    /**
     * 失败响应
     */
    public static <T> Result<T> error() {
        return error("操作失败");
    }
    
    /**
     * 失败响应带消息
     */
    public static <T> Result<T> error(String message) {
        return error(ResultCode.ERROR.getCode(), message);
    }
    
    /**
     * 失败响应带错误码和消息
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
    
    /**
     * 根据结果码构建响应
     */
    public static <T> Result<T> build(ResultCode resultCode) {
        return build(resultCode, null);
    }
    
    /**
     * 根据结果码构建响应带数据
     */
    public static <T> Result<T> build(ResultCode resultCode, T data) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }
}