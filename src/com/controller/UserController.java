package com.controller;

import com.model.entity.Order;
import com.model.entity.User;
import com.model.service.OrderService;
import com.model.service.UserService;
import com.model.service.impl.OrderServiceImpl;
import com.model.service.impl.UserServiceImpl;
import com.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * 用户控制器 - 登录、注册、个人信息
 */
public class UserController extends BaseServlet {

    private UserService userService = new UserServiceImpl();
    private OrderService orderService = new OrderServiceImpl();

    /**
     * 显示登录页
     */
    public void loginPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
    }

    /**
     * 登录
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null || username.trim().isEmpty()) {
            req.setAttribute("error", "用户名和密码不能为空");
            req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
            return;
        }

        User user = userService.login(username, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            // 获取登录前访问的页面
            String returnUrl = (String) session.getAttribute("returnUrl");
            session.removeAttribute("returnUrl");

            if ("admin".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else if (returnUrl != null && !returnUrl.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + returnUrl);
            } else {
                resp.sendRedirect(req.getContextPath() + "/home/index");
            }
        } else {
            req.setAttribute("error", "用户名或密码错误");
            req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
        }
    }

    /**
     * 显示注册页
     */
    public void registerPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
    }

    /**
     * 注册
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPwd = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        if (username == null || username.trim().isEmpty()) {
            req.setAttribute("error", "用户名不能为空");
            req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
            return;
        }
        if (password == null || password.length() < 3) {
            req.setAttribute("error", "密码至少3位");
            req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
            return;
        }
        if (!password.equals(confirmPwd)) {
            req.setAttribute("error", "两次密码不一致");
            req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password);
        user.setEmail(email != null && !email.trim().isEmpty() ? email.trim() : null);
        user.setPhone(phone != null && !phone.trim().isEmpty() ? phone.trim() : null);

        try {
            boolean ok = userService.register(user);
            if (ok) {
                // URLEncoder.encode 解决 redirect 中文乱码问题
                String msg = URLEncoder.encode("注册成功，请登录", "UTF-8");
                resp.sendRedirect(req.getContextPath() + "/views/user/login.jsp?msg=" + msg);
            } else {
                req.setAttribute("error", "注册失败，用户名可能已存在，或数据库连接异常");
                req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "系统异常：" + e.getMessage());
            req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
        }
    }

    /**
     * 退出登录
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/views/user/login.jsp");
    }

    /**
     * 个人中心
     */
    public void profile(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer userId = WebUtils.getLoginUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/views/user/login.jsp");
            return;
        }
        User user = userService.findById(userId);
        req.setAttribute("user", user);
        java.util.List<Order> orders = orderService.getUserOrders(userId);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
    }

    /**
     * 更新个人信息
     */
    public void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer userId = WebUtils.getLoginUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/views/user/login.jsp");
            return;
        }

        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        User user = userService.findById(userId);
        if (user != null) {
            user.setEmail(email);
            user.setPhone(phone);
            userService.updateProfile(user);
            req.setAttribute("message", "更新成功");
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
    }
}