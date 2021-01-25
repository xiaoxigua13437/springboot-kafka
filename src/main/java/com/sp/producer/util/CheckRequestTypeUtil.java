package com.sp.producer.util;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 自定义判断请求类型
 *
 * @author yushu.zhao
 * @create 2020-08-21 14:28
 */
public class CheckRequestTypeUtil {


    public static final String LOGGER_RETURN = "_logger_return";

    private CheckRequestTypeUtil() {
    }

    /**
     * 获取客户端ip地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }

    public static String getClientUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        userAgent = (userAgent == null ? "未知请求头"
                : (userAgent.length() > 256 ? userAgent.substring(256) : userAgent));
        return userAgent;
    }

    /**
     * 判断是否为ajax请求
     */
    public static String getRequestType(HttpServletRequest request) {
        if (request.getHeader("X-Requested-With") == null) {
            return HTTP_REQ;
        } else if (request.getHeader("X-Requested-With").toLowerCase().contains("xmlhttprequest")) {
            return AJAX_REQ;
        } else {
            return OTHER_REQ;
        }
    }

    public static final String HTTP_REQ = "普通Http请求";
    public static final String AJAX_REQ = "Ajax请求";
    public static final String OTHER_REQ = "其它请求";
}