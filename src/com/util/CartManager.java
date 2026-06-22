package com.util;

import com.model.entity.Ticket;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 购物车管理工具类 (基于 HttpSession)
 * 购物车存储在 Session 中，key 为 "cart"
 */
public class CartManager {

    private static final String CART_KEY = "cart";

    /**
     * 购物车项（内部类）
     */
    public static class CartItem {
        private Ticket ticket;
        private int quantity;

        public CartItem() {}

        public CartItem(Ticket ticket, int quantity) {
            this.ticket = ticket;
            this.quantity = quantity;
        }

        public Ticket getTicket() { return ticket; }
        public void setTicket(Ticket ticket) { this.ticket = ticket; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        /** 小计金额 */
        public double getSubtotal() {
            return ticket.getPrice() * quantity;
        }
    }

    // ===== 购物车操作方法 =====

    /**
     * 获取购物车 (Map: ticketId -> CartItem)
     */
    @SuppressWarnings("unchecked")
    private static Map<Integer, CartItem> getCartMap(HttpSession session) {
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute(CART_KEY);
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute(CART_KEY, cart);
        }
        return cart;
    }

    /**
     * 添加票品到购物车
     */
    public static void addItem(HttpSession session, Ticket ticket, int quantity) {
        Map<Integer, CartItem> cart = getCartMap(session);
        int tid = ticket.getId();
        if (cart.containsKey(tid)) {
            CartItem item = cart.get(tid);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cart.put(tid, new CartItem(ticket, quantity));
        }
    }

    /**
     * 更新购物车中某个票品的数量
     */
    public static void updateItem(HttpSession session, int ticketId, int quantity) {
        Map<Integer, CartItem> cart = getCartMap(session);
        if (cart.containsKey(ticketId)) {
            if (quantity <= 0) {
                cart.remove(ticketId);
            } else {
                cart.get(ticketId).setQuantity(quantity);
            }
        }
    }

    /**
     * 移除购物车中的某个票品
     */
    public static void removeItem(HttpSession session, int ticketId) {
        Map<Integer, CartItem> cart = getCartMap(session);
        cart.remove(ticketId);
    }

    /**
     * 清空购物车
     */
    public static void clear(HttpSession session) {
        session.removeAttribute(CART_KEY);
    }

    /**
     * 获取购物车中所有项
     */
    public static List<CartItem> getItems(HttpSession session) {
        Map<Integer, CartItem> cart = getCartMap(session);
        return new ArrayList<>(cart.values());
    }

    /**
     * 获取购物车总金额
     */
    public static double getTotal(HttpSession session) {
        double total = 0;
        for (CartItem item : getItems(session)) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * 获取购物车商品数量
     */
    public static int getItemCount(HttpSession session) {
        Map<Integer, CartItem> cart = getCartMap(session);
        return cart.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    /**
     * 判断购物车是否为空
     */
    public static boolean isEmpty(HttpSession session) {
        return getCartMap(session).isEmpty();
    }
}
