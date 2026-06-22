package com.model.entity;

import java.time.LocalDateTime;

/**
 * 票品实体类
 */
public class Ticket {
    private Integer id;
    private String name;
    private String type;         // concert / 体育 / 电影 / 演出
    private String description;
    private double price;
    private int stock;
    private String image;
    private String status;       // on_sale / sold_out / off
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Ticket() {}

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    /** 是否在售 */
    public boolean isOnSale() {
        return "on_sale".equals(status);
    }

    @Override
    public String toString() {
        return "Ticket{id=" + id + ", name='" + name + "', price=" + price + ", stock=" + stock + "}";
    }
}
