package com.model.dao;

import com.model.entity.Ticket;
import com.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 票品数据访问层
 */
public class TicketDao {

    /**
     * 查询所有在售票品
     */
    public List<Ticket> findAllOnSale() {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE status = 'on_sale' ORDER BY create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapTicket(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据类型查询在售票品
     */
    public List<Ticket> findByType(String type) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE status = 'on_sale' AND type = ? ORDER BY create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTicket(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 搜索票品（按名称模糊匹配）
     */
    public List<Ticket> search(String keyword) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE status = 'on_sale' AND name LIKE ? ORDER BY create_time DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTicket(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据 ID 查询票品
     */
    public Ticket findById(int id) {
        String sql = "SELECT * FROM ticket WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTicket(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据名称查询票品
     */
    public Ticket findByName(String name) {
        String sql = "SELECT * FROM ticket WHERE name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTicket(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 插入票品（管理员用）
     */
    public int insert(Ticket ticket) {
        String sql = "INSERT INTO ticket (name, type, description, price, stock, image, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ticket.getName());
            ps.setString(2, ticket.getType());
            ps.setString(3, ticket.getDescription());
            ps.setDouble(4, ticket.getPrice());
            ps.setInt(5, ticket.getStock());
            ps.setString(6, ticket.getImage());
            ps.setString(7, ticket.getStatus() != null ? ticket.getStatus() : "on_sale");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) ticket.setId(rs.getInt(1));
                }
            }
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 更新票品（管理员用）
     */
    public int update(Ticket ticket) {
        String sql = "UPDATE ticket SET name=?, type=?, description=?, price=?, stock=?, image=?, status=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticket.getName());
            ps.setString(2, ticket.getType());
            ps.setString(3, ticket.getDescription());
            ps.setDouble(4, ticket.getPrice());
            ps.setInt(5, ticket.getStock());
            ps.setString(6, ticket.getImage());
            ps.setString(7, ticket.getStatus());
            ps.setInt(8, ticket.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除票品（管理员用）
     */
    public int delete(int id) {
        String sql = "DELETE FROM ticket WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 更新库存
     */
    public int updateStock(int id, int stock) {
        String sql = "UPDATE ticket SET stock = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stock);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询所有票品（管理员用，含下架）
     */
    public List<Ticket> findAll() {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket ORDER BY type ASC, id ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapTicket(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 统计在售票品数量
     */
    public int countOnSale() {
        String sql = "SELECT COUNT(*) FROM ticket WHERE status = 'on_sale'";
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
     * 按名称搜索所有票品（管理员用）
     */
    public List<Ticket> searchAll(String keyword) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE name LIKE ? ORDER BY type ASC, id ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTicket(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Ticket mapTicket(ResultSet rs) throws SQLException {
        Ticket t = new Ticket();
        t.setId(rs.getInt("id"));
        t.setName(rs.getString("name"));
        t.setType(rs.getString("type"));
        t.setDescription(rs.getString("description"));
        t.setPrice(rs.getDouble("price"));
        t.setStock(rs.getInt("stock"));
        t.setImage(rs.getString("image"));
        t.setStatus(rs.getString("status"));
        if (rs.getTimestamp("create_time") != null) {
            t.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        }
        if (rs.getTimestamp("update_time") != null) {
            t.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        }
        return t;
    }
}
