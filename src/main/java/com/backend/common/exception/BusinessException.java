package com.backend.common.exception;

import com.backend.common.result.ResultCode;
import lombok.Getter;

/**
 * 业务异常类
 * 
 * @author backend
 * @since 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {
    
    /**
     * 错误码
     */
    private final Integer code;
    
    /**
     * 错误消息
     */
    private final String message;
    
    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.BUSINESS_ERROR.getCode();
        this.message = message;
    }
    
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
    
    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
        this.message = message;
    }
    
    /**
     * 静态工厂方法
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }
    
    public static BusinessException of(Integer code, String message) {
        return new BusinessException(code, message);
    }
    
    public static BusinessException of(ResultCode resultCode) {
        return new BusinessException(resultCode);
    }
    
    public static BusinessException of(ResultCode resultCode, String message) {
        return new BusinessException(resultCode, message);
    }
}