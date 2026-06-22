package com.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局编码过滤器
 * 设置请求和响应的字符编码为 UTF-8
 */
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 设置请求编码
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
