package com.model.dao;

import com.model.entity.User;
import com.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问层
 */
public class UserDao {

    /**
     * 根据用户名查询用户
     */
    public User findByUsername(String username) {
        String sql = "SELECT * FROM `user` WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据 ID 查询用户
     */
    public User findById(int id) {
        String sql = "SELECT * FROM `user` WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户名密码查询（登录验证）
     */
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM `user` WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 插入用户（注册）
     */
    public int insert(User user) {
        String sql = "INSERT INTO `user` (username, password, email, phone, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole() != null ? user.getRole() : "user");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
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
     * 更新用户信息
     */
    public int update(User user) {
        String sql = "UPDATE `user` SET email = ?, phone = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPhone());
            ps.setInt(3, user.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询所有用户（管理员用）
     */
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM `user` ORDER BY id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        if (rs.getTimestamp("create_time") != null) {
            user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        }
        if (rs.getTimestamp("update_time") != null) {
            user.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        }
        return user;
    }
}
