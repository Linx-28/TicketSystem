package com.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Servlet 基类
 * 根据以下顺序确定要调用的方法名：
 * 1. 请求参数 method（如 ?method=login）
 * 2. 请求路径信息（如 /user/register 提取 register）
 * 3. 默认值 "index"
 * 子类方法签名: public void methodName(HttpServletRequest req, HttpServletResponse resp)
 */
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // 确定方法名：优先取 method 参数，其次从路径提取，最后默认 index
        String methodName = req.getParameter("method");
        if (methodName == null || methodName.trim().isEmpty()) {
            methodName = getMethodNameFromPath(req);
        }
        if (methodName == null || methodName.trim().isEmpty()) {
            methodName = "index";
        }

        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown method: " + methodName);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    /**
     * 从请求路径中提取方法名
     * 例如 /user/register 提取为 register
     */
    private String getMethodNameFromPath(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            // 去掉前导 "/"
            String name = pathInfo.substring(1);
            // 如果还有子路径（如 /user/login/xxx），只取第一段
            int slash = name.indexOf('/');
            if (slash > 0) {
                name = name.substring(0, slash);
            }
            return name;
        }
        return null;
    }
}
