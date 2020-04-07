package com.zjstudio.happly_birthday.common;

import lombok.Data;

/**
 * <p>
 * TODO Controller层的日志封装类
 * </p>
 *
 * @author 添柴灬少年
 * @version 1.0
 * @date 2019/12/27 16:25
 **/
@Data
public class WebLog {
    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作时间
     */
    private Long startTime;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    private Object parameter;

    private Object result;
}
