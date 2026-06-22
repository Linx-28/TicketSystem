package com.model.dao;

import com.model.entity.OrderItem;
import com.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单项数据访问层
 */
public class OrderItemDao {

    /**
     * 批量插入订单项
     */
    public int[] batchInsert(List<OrderItem> items) {
        String sql = "INSERT INTO order_item (order_id, ticket_name, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (OrderItem item : items) {
                ps.setInt(1, item.getOrderId());
                ps.setString(2, item.getTicketName());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getPrice());
                ps.addBatch();
            }
            return ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[0];
    }

    /**
     * 根据订单 ID 查询订单项列表
     */
    public List<OrderItem> findByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapOrderItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private OrderItem mapOrderItem(ResultSet rs) throws SQLException {
        OrderItem item = new OrderItem();
        item.setId(rs.getInt("id"));
        item.setOrderId(rs.getInt("order_id"));
        item.setTicketName(rs.getString("ticket_name"));
        item.setQuantity(rs.getInt("quantity"));
        item.setPrice(rs.getDouble("price"));
        return item;
    }
}
