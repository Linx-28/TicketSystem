package com.controller;

import com.model.entity.Order;
import com.model.service.OrderService;
import com.model.service.impl.OrderServiceImpl;
import com.util.CartManager;
import com.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单控制器 - 购物车、下单、订单历史
 */
public class OrderController extends BaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 购物车页面
     */
    public void cart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("cartItems", CartManager.getItems(req.getSession()));
        req.setAttribute("cartTotal", CartManager.getTotal(req.getSession()));
        req.setAttribute("cartCount", CartManager.getItemCount(req.getSession()));
        req.getRequestDispatcher("/views/order/cart.jsp").forward(req, resp);
    }

    /**
     * 更新购物车商品数量
     */
    public void updateCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int ticketId = WebUtils.getInt(req, "ticketId", 0);
        int quantity = WebUtils.getInt(req, "quantity", 1);
        if (ticketId > 0) {
            CartManager.updateItem(req.getSession(), ticketId, quantity);
        }
        resp.sendRedirect(req.getContextPath() + "/order/cart");
    }

    /**
     * 从购物车移除
     */
    public void removeFromCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int ticketId = WebUtils.getInt(req, "ticketId", 0);
        if (ticketId > 0) {
            CartManager.removeItem(req.getSession(), ticketId);
        }
        resp.sendRedirect(req.getContextPath() + "/order/cart");
    }

    /**
     * 结算页面
     */
    public void checkout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (CartManager.isEmpty(req.getSession())) {
            resp.sendRedirect(req.getContextPath() + "/order/cart");
            return;
        }
        req.setAttribute("cartItems", CartManager.getItems(req.getSession()));
        req.setAttribute("cartTotal", CartManager.getTotal(req.getSession()));
        req.getRequestDispatcher("/views/order/checkout.jsp").forward(req, resp);
    }

    /**
     * 提交订单
     */
    public void submitOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer userId = WebUtils.getLoginUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/views/user/login.jsp");
            return;
        }

        if (CartManager.isEmpty(req.getSession())) {
            req.setAttribute("error", "购物车为空");
            req.getRequestDispatcher("/views/order/cart.jsp").forward(req, resp);
            return;
        }

        List<CartManager.CartItem> items = CartManager.getItems(req.getSession());
        boolean ok = orderService.placeOrder(userId, items);

        if (ok) {
            CartManager.clear(req.getSession());
            resp.sendRedirect(req.getContextPath() + "/order/history");
        } else {
            req.setAttribute("error", "下单失败，请检查库存");
            req.getRequestDispatcher("/views/order/checkout.jsp").forward(req, resp);
        }
    }

    /**
     * 订单历史
     */
    public void history(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer userId = WebUtils.getLoginUserId(req);
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/views/user/login.jsp");
            return;
        }
        List<Order> orders = orderService.getUserOrders(userId);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/order/history.jsp").forward(req, resp);
    }

    /**
     * 查看订单详情
     */
    public void detail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int orderId = WebUtils.getInt(req, "id", 0);
        Integer userId = WebUtils.getLoginUserId(req);
        if (orderId <= 0 || userId == null) {
            resp.sendRedirect(req.getContextPath() + "/order/history");
            return;
        }
        Order order = orderService.findById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            resp.sendRedirect(req.getContextPath() + "/order/history");
            return;
        }
        req.setAttribute("order", order);
        req.getRequestDispatcher("/views/order/history.jsp").forward(req, resp);
    }

    /**
     * 支付订单
     */
    public void pay(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int orderId = WebUtils.getInt(req, "id", 0);
        Integer userId = WebUtils.getLoginUserId(req);
        if (orderId > 0 && userId != null) {
            orderService.payOrder(orderId, userId);
        }
        resp.sendRedirect(req.getContextPath() + "/order/history");
    }

    /**
     * 取消订单
     */
    public void cancel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int orderId = WebUtils.getInt(req, "id", 0);
        Integer userId = WebUtils.getLoginUserId(req);
        if (orderId > 0 && userId != null) {
            orderService.cancelOrder(orderId, userId);
        }
        resp.sendRedirect(req.getContextPath() + "/order/history");
    }
}
