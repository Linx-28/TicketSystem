package com.model.service;

import com.model.entity.Order;
import java.util.List;

public interface OrderService {
    /** 提交订单（从购物车） */
    boolean placeOrder(int userId, List<com.util.CartManager.CartItem> cartItems);
    /** 根据ID查询订单（含订单项） */
    Order findById(int id);
    /** 获取用户订单列表 */
    List<Order> getUserOrders(int userId);
    /** 取消订单 */
    boolean cancelOrder(int orderId, int userId);
    /** 支付订单 */
    boolean payOrder(int orderId, int userId);
    /** 获取所有订单（管理员） */
    List<Order> getAllOrders();
    /** 处理订单（管理员：发货/完成） */
    boolean processOrder(int orderId, String status);
    /** 统计订单总数 */
    int countAllOrders();
    /** 统计今日订单 */
    int countTodayOrders();
    /** 统计总销售额 */
    double sumTotalSales();
}
