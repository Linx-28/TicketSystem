package com.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 登录验证过滤器
 * 检查用户是否已登录，排除公开页面（首页、登录、注册、静态资源）
 */
public class LoginFilter implements Filter {

    // 公开页面路径前缀（不需要登录即可访问）
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/views/user/login.jsp",
            "/views/user/register.jsp",
            "/views/error/",
            "/static/",
            "/index.html"
    );

    // 公开 Servlet 路径
    private static final List<String> PUBLIC_SERVLETS = Arrays.asList(
            "/user/login",
            "/user/register"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // 1. 公开路径直接放行
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. 检查登录状态
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // 未登录，保存原始请求路径，登录后跳回
            HttpSession writeSession = req.getSession(true);
            writeSession.setAttribute("returnUrl", path);
            resp.sendRedirect(req.getContextPath() + "/views/user/login.jsp");
            return;
        }

        // 3. 已登录，放行
        chain.doFilter(request, response);
    }

    /**
     * 判断是否为公开路径
     */
    private boolean isPublicPath(String path) {
        // 检查页面路径
        for (String pp : PUBLIC_PATHS) {
            if (path.startsWith(pp)) {
                return true;
            }
        }
        // 检查 Servlet 路径
        for (String ps : PUBLIC_SERVLETS) {
            if (path.equals(ps) || path.startsWith(ps + "?")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {}
}
