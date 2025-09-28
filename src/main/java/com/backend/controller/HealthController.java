package com.backend.controller;

import com.backend.common.result.Result;
import com.backend.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 * 
 * @author backend
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/health")
public class HealthController {

    /**
     * 健康检查
     */
    @GetMapping
    public Result<Map<String, Object>> health() {
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("timestamp", DateUtils.formatNow());
        healthInfo.put("application", "API Foundation");
        healthInfo.put("version", "1.0.0");
        
        return Result.success("系统运行正常", healthInfo);
    }

    /**
     * 系统信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> systemInfo = new HashMap<>();
        
        // 应用信息
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("name", "API Foundation");
        appInfo.put("version", "1.0.0");
        appInfo.put("description", "基础API服务");
        systemInfo.put("application", appInfo);
        
        // 系统信息
        Map<String, Object> sysInfo = new HashMap<>();
        sysInfo.put("java.version", System.getProperty("java.version"));
        sysInfo.put("java.vendor", System.getProperty("java.vendor"));
        sysInfo.put("os.name", System.getProperty("os.name"));
        sysInfo.put("os.version", System.getProperty("os.version"));
        sysInfo.put("os.arch", System.getProperty("os.arch"));
        systemInfo.put("system", sysInfo);
        
        // 运行时信息
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> runtimeInfo = new HashMap<>();
        runtimeInfo.put("processors", runtime.availableProcessors());
        runtimeInfo.put("totalMemory", runtime.totalMemory());
        runtimeInfo.put("freeMemory", runtime.freeMemory());
        runtimeInfo.put("maxMemory", runtime.maxMemory());
        systemInfo.put("runtime", runtimeInfo);
        
        return Result.success("系统信息获取成功", systemInfo);
    }

    /**
     * Ping接口
     */
    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.success("pong");
    }
}