package com.model.service.impl;

import com.model.dao.OrderDao;
import com.model.dao.OrderItemDao;
import com.model.dao.TicketDao;
import com.model.entity.Order;
import com.model.entity.OrderItem;
import com.model.entity.Ticket;
import com.model.service.OrderService;
import com.util.CartManager;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDao();
    private OrderItemDao orderItemDao = new OrderItemDao();
    private TicketDao ticketDao = new TicketDao();

    @Override
    public boolean placeOrder(int userId, List<CartManager.CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) return false;

        // 计算总价
        double total = cartItems.stream().mapToDouble(CartManager.CartItem::getSubtotal).sum();

        // 生成订单号
        String orderNo = generateOrderNo();

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(total);
        order.setStatus("pending");

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 1. 插入订单
            String sql = "INSERT INTO `order` (order_no, user_id, total_price, status) VALUES (?, ?, ?, ?)";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, orderNo);
            ps.setInt(2, userId);
            ps.setDouble(3, total);
            ps.setString(4, "pending");
            int rows = ps.executeUpdate();
            if (rows == 0) {
                conn.rollback();
                return false;
            }
            java.sql.ResultSet rs = ps.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) orderId = rs.getInt(1);
            rs.close();
            ps.close();

            // 2. 插入订单项 + 扣减库存
            String itemSql = "INSERT INTO order_item (order_id, ticket_name, quantity, price) VALUES (?, ?, ?, ?)";
            String stockSql = "UPDATE ticket SET stock = stock - ? WHERE id = ? AND stock >= ?";
            java.sql.PreparedStatement itemPs = conn.prepareStatement(itemSql);
            java.sql.PreparedStatement stockPs = conn.prepareStatement(stockSql);

            for (CartManager.CartItem cartItem : cartItems) {
                // 插入订单项
                itemPs.setInt(1, orderId);
                itemPs.setString(2, cartItem.getTicket().getName());
                itemPs.setInt(3, cartItem.getQuantity());
                itemPs.setDouble(4, cartItem.getTicket().getPrice());
                itemPs.addBatch();

                // 扣减库存（检查库存是否充足）
                stockPs.setInt(1, cartItem.getQuantity());
                stockPs.setInt(2, cartItem.getTicket().getId());
                stockPs.setInt(3, cartItem.getQuantity());
                stockPs.addBatch();
            }

            int[] itemResults = itemPs.executeBatch();
            int[] stockResults = stockPs.executeBatch();
            itemPs.close();
            stockPs.close();

            // 检查库存扣减是否都成功
            for (int r : stockResults) {
                if (r == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            order.setId(orderId);
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public Order findById(int id) {
        Order order = orderDao.findById(id);
        if (order != null) {
            order.setItems(orderItemDao.findByOrderId(order.getId()));
        }
        return order;
    }

    @Override
    public List<Order> getUserOrders(int userId) {
        List<Order> orders = orderDao.findByUserId(userId);
        for (Order order : orders) {
            order.setItems(orderItemDao.findByOrderId(order.getId()));
        }
        return orders;
    }

    @Override
    public boolean cancelOrder(int orderId, int userId) {
        Order order = orderDao.findById(orderId);
        if (order == null || !order.getUserId().equals(userId)) return false;
        if (!"pending".equals(order.getStatus())) return false;

        int result = orderDao.updateStatus(orderId, "cancelled");
        if (result > 0) {
            // 恢复库存
            List<OrderItem> items = orderItemDao.findByOrderId(orderId);
            for (OrderItem item : items) {
                List<Ticket> tickets = ticketDao.search(item.getTicketName());
                if (!tickets.isEmpty()) {
                    Ticket ticket = tickets.get(0);
                    ticketDao.updateStock(ticket.getId(), ticket.getStock() + item.getQuantity());
                }
            }
        }
        return result > 0;
    }

    @Override
    public boolean payOrder(int orderId, int userId) {
        Order order = orderDao.findById(orderId);
        if (order == null || !order.getUserId().equals(userId)) return false;
        if (!"pending".equals(order.getStatus())) return false;

        return orderDao.updateStatus(orderId, "paid") > 0;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderDao.findAll();
        for (Order order : orders) {
            order.setItems(orderItemDao.findByOrderId(order.getId()));
        }
        return orders;
    }

    @Override
    public boolean processOrder(int orderId, String status) {
        return orderDao.updateStatus(orderId, status) > 0;
    }

    @Override
    public int countAllOrders() {
        return orderDao.countAll();
    }

    @Override
    public int countTodayOrders() {
        return orderDao.countToday();
    }

    @Override
    public double sumTotalSales() {
        return orderDao.sumTotalPrice();
    }

    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                + (int)(Math.random() * 1000);
    }
}
