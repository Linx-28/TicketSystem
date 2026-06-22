package com.model.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单实体类
 */
public class Order {
    private Integer id;
    private String orderNo;      // 订单号
    private Integer userId;
    private String username;     // 冗余字段，用于显示
    private double totalPrice;
    private String status;       // pending / paid / cancelled
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 非数据库字段 - 订单项列表
    private List<OrderItem> items;

    public Order() {}

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    /** 状态描述 */
    public String getStatusDesc() {
        switch (status) {
            case "pending":   return "待支付";
            case "paid":      return "已支付";
            case "cancelled": return "已取消";
            default:          return status;
        }
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", orderNo='" + orderNo + "', status='" + status + "'}";
    }
}
