package com.controller;

import com.model.entity.Ticket;
import com.model.service.TicketService;
import com.model.service.impl.TicketServiceImpl;
import com.util.CartManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 票务控制器 - 列表、详情、搜索
 */
public class TicketController extends BaseServlet {

    private TicketService ticketService = new TicketServiceImpl();

    /**
     * 票品列表（首页也用这个）
     */
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String type = req.getParameter("type");
        List<Ticket> ticketList;

        if (type != null && !type.trim().isEmpty()) {
            ticketList = ticketService.getByType(type);
            req.setAttribute("currentType", type);
        } else {
            ticketList = ticketService.getOnSaleList();
        }

        req.setAttribute("ticketList", ticketList);
        req.getRequestDispatcher("/views/ticket/list.jsp").forward(req, resp);
    }

    /**
     * 搜索票品
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String keyword = req.getParameter("keyword");
        List<Ticket> ticketList;

        if (keyword != null && !keyword.trim().isEmpty()) {
            ticketList = ticketService.search(keyword.trim());
            req.setAttribute("keyword", keyword);
        } else {
            ticketList = ticketService.getOnSaleList();
        }

        req.setAttribute("ticketList", ticketList);
        req.getRequestDispatcher("/views/ticket/list.jsp").forward(req, resp);
    }

    /**
     * 票品详情
     */
    public void detail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "票品不存在");
            return;
        }
        req.setAttribute("ticket", ticket);
        req.getRequestDispatcher("/views/ticket/detail.jsp").forward(req, resp);
    }

    /**
     * 添加到购物车
     */
    public void addToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int ticketId = Integer.parseInt(req.getParameter("id"));
        int quantity = 1;
        if (req.getParameter("quantity") != null) {
            quantity = Integer.parseInt(req.getParameter("quantity"));
        }

        Ticket ticket = ticketService.findById(ticketId);
        if (ticket != null && ticket.isOnSale()) {
            CartManager.addItem(req.getSession(), ticket, quantity);
            req.getSession().setAttribute("cartSuccess", "已将「" + ticket.getName() + "」加入购物车！");
        }

        // 跳转回来源页或购物车
        String referer = req.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/ticket/list");
        }
    }
}
