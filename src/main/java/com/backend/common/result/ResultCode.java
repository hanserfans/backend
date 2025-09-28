package com.backend.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举
 * 
 * @author backend
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    
    // 成功
    SUCCESS(200, "操作成功"),
    
    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    CONFLICT(409, "资源冲突"),
    VALIDATION_ERROR(422, "参数校验失败"),
    
    // 服务器错误
    ERROR(500, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    
    // 业务错误码 (6000-6999)
    BUSINESS_ERROR(6000, "业务处理失败"),
    DATA_NOT_FOUND(6001, "数据不存在"),
    DATA_ALREADY_EXISTS(6002, "数据已存在"),
    OPERATION_NOT_ALLOWED(6003, "操作不被允许"),
    
    // 数据库错误码 (7000-7999)
    DATABASE_ERROR(7000, "数据库操作失败"),
    DATABASE_CONNECTION_ERROR(7001, "数据库连接失败"),
    
    // 外部服务错误码 (8000-8999)
    EXTERNAL_SERVICE_ERROR(8000, "外部服务调用失败"),
    NETWORK_ERROR(8001, "网络连接异常");
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 状态消息
     */
    private final String message;
}