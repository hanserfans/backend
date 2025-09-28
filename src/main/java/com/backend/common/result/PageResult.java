package com.backend.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * 分页响应结果类
 * 
 * @author backend
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    
    /**
     * 数据列表
     */
    private List<T> records;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 当前页码
     */
    private Long current;
    
    /**
     * 每页大小
     */
    private Long size;
    
    /**
     * 总页数
     */
    private Long pages;
    
    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;
    
    /**
     * 是否有下一页
     */
    private Boolean hasNext;
    
    /**
     * 构造分页结果
     */
    public static <T> PageResult<T> of(List<T> records, Long total, Long current, Long size) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setRecords(records);
        pageResult.setTotal(total);
        pageResult.setCurrent(current);
        pageResult.setSize(size);
        
        // 计算总页数
        Long pages = (total + size - 1) / size;
        pageResult.setPages(pages);
        
        // 计算是否有上一页和下一页
        pageResult.setHasPrevious(current > 1);
        pageResult.setHasNext(current < pages);
        
        return pageResult;
    }
    
    /**
     * 空分页结果
     */
    public static <T> PageResult<T> empty() {
        return of(Collections.emptyList(), 0L, 1L, 10L);
    }
}