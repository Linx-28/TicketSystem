package com.model.entity;

/**
 * 订单项实体类
 */
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private String ticketName;
    private int quantity;
    private double price;

    public OrderItem() {}

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public String getTicketName() { return ticketName; }
    public void setTicketName(String ticketName) { this.ticketName = ticketName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    /** 小计 */
    public double getSubtotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{id=" + id + ", ticketName=" + ticketName + ", quantity=" + quantity + "}";
    }
}
