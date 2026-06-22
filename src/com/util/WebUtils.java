package com.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web 通用工具类
 */
public class WebUtils {

    /**
     * 获取请求参数（字符串），如果为 null 返回默认值
     */
    public static String getString(HttpServletRequest req, String name, String defaultValue) {
        String val = req.getParameter(name);
        return (val != null && !val.trim().isEmpty()) ? val.trim() : defaultValue;
    }

    /**
     * 获取请求参数（整数）
     */
    public static int getInt(HttpServletRequest req, String name, int defaultValue) {
        try {
            return Integer.parseInt(req.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取请求参数（浮点数）
     */
    public static double getDouble(HttpServletRequest req, String name, double defaultValue) {
        try {
            return Double.parseDouble(req.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 重定向（带上下文路径）
     */
    public static void redirect(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        resp.sendRedirect(req.getContextPath() + path);
    }

    /**
     * 转发
     */
    public static void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws Exception {
        req.getRequestDispatcher(path).forward(req, resp);
    }

    /**
     * 返回 JSON 响应
     */
    public static void writeJson(HttpServletResponse resp, String json) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 返回普通文本响应
     */
    public static void writeText(HttpServletResponse resp, String text) throws IOException {
        resp.setContentType("text/plain;charset=utf-8");
        resp.getWriter().write(text);
    }

    /**
     * 获取当前登录用户 ID
     */
    public static Integer getLoginUserId(HttpServletRequest req) {
        Object uid = req.getSession().getAttribute("userId");
        return uid != null ? (Integer) uid : null;
    }

    /**
     * 获取当前登录用户名
     */
    public static String getLoginUsername(HttpServletRequest req) {
        Object uname = req.getSession().getAttribute("username");
        return uname != null ? (String) uname : null;
    }

    /**
     * 判断是否为管理员
     */
    public static boolean isAdmin(HttpServletRequest req) {
        Object role = req.getSession().getAttribute("role");
        return "admin".equals(role);
    }
}
