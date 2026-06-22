package com.model.dao;

import com.model.entity.Order;
import com.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单数据访问层
 */
public class OrderDao {

    /**
     * 插入订单
     */
    public int insert(Order order) {
        String sql = "INSERT INTO `order` (order_no, user_id, total_price, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getOrderNo());
            ps.setInt(2, order.getUserId());
            ps.setDouble(3, order.getTotalPrice());
            ps.setString(4, order.getStatus() != null ? order.getStatus() : "pending");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getInt(1));
                    }
                }
            }
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据 ID 查询订单
     */
    public Order findById(int id) {
        String sql = "SELECT o.*, u.username FROM `order` o JOIN `user` u ON o.user_id = u.id WHERE o.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据订单号查询
     */
    public Order findByOrderNo(String orderNo) {
        String sql = "SELECT o.*, u.username FROM `order` o JOIN `user` u ON o.user_id = u.id WHERE o.order_no = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询用户的所有订单
     */
    public List<Order> findByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.username FROM `order` o JOIN `user` u ON o.user_id = u.id WHERE o.user_id = ? ORDER BY o.create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询所有订单（管理员用）
     */
    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.username FROM `order` o JOIN `user` u ON o.user_id = u.id ORDER BY o.create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 更新订单状态
     */
    public int updateStatus(int id, String status) {
        String sql = "UPDATE `order` SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 统计订单总数
     */
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM `order`";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 统计今日新增订单数
     */
    public int countToday() {
        String sql = "SELECT COUNT(*) FROM `order` WHERE DATE(create_time) = CURDATE()";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 统计总销售额
     */
    public double sumTotalPrice() {
        String sql = "SELECT COALESCE(SUM(total_price), 0) FROM `order` WHERE status = 'paid'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setOrderNo(rs.getString("order_no"));
        o.setUserId(rs.getInt("user_id"));
        o.setUsername(rs.getString("username"));
        o.setTotalPrice(rs.getDouble("total_price"));
        o.setStatus(rs.getString("status"));
        if (rs.getTimestamp("create_time") != null) {
            o.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        }
        if (rs.getTimestamp("update_time") != null) {
            o.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        }
        return o;
    }
}
