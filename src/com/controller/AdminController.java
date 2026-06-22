package com.controller;

import com.model.entity.Order;
import com.model.entity.Ticket;
import com.model.service.OrderService;
import com.model.service.TicketService;
import com.model.service.impl.OrderServiceImpl;
import com.model.service.impl.TicketServiceImpl;
import com.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 管理后台控制器
 */
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class AdminController extends BaseServlet {

    private TicketService ticketService = new TicketServiceImpl();
    private OrderService orderService = new OrderServiceImpl();

    private static final String UPLOAD_DIR = "static/images";

    /**
     * 处理图片上传，返回相对路径
     */
    private String handleImageUpload(HttpServletRequest req, String fieldName) throws IOException, ServletException {
        Part part = req.getPart(fieldName);
        if (part == null || part.getSize() == 0) {
            return null;
        }
        String originalName = part.getSubmittedFileName();
        if (originalName == null || originalName.isEmpty()) {
            return null;
        }
        String ext = "";
        int dot = originalName.lastIndexOf('.');
        if (dot >= 0) {
            ext = originalName.substring(dot);
        }
        String fileName = UUID.randomUUID().toString().replace("-", "") + ext;

        String uploadPath = req.getServletContext().getRealPath("/") + File.separator + UPLOAD_DIR;
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        part.write(uploadPath + File.separator + fileName);

        return "/" + UPLOAD_DIR + "/" + fileName;
    }

    /**
     * 权限检查
     */
    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!WebUtils.isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/views/user/login.jsp");
            return false;
        }
        return true;
    }

    /**
     * 仪表盘 - 数据统计
     */
    public void dashboard(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;

        int ticketCount = ticketService.getOnSaleList().size();
        int orderCount = orderService.countAllOrders();
        int todayOrder = orderService.countTodayOrders();
        double totalSales = orderService.sumTotalSales();

        req.setAttribute("ticketCount", ticketCount);
        req.setAttribute("orderCount", orderCount);
        req.setAttribute("todayOrder", todayOrder);
        req.setAttribute("totalSales", totalSales);
        req.getRequestDispatcher("/views/admin/dashboard.jsp").forward(req, resp);
    }

    // ===== 票品管理 =====

    /**
     * 票品管理页
     */
    public void ticketManage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;

        String keyword = req.getParameter("keyword");
        List<Ticket> tickets;
        if (keyword != null && !keyword.trim().isEmpty()) {
            tickets = ticketService.searchAllTickets(keyword.trim());
            req.setAttribute("keyword", keyword);
        } else {
            tickets = ticketService.getAllTickets();
        }
        req.setAttribute("tickets", tickets);
        req.getRequestDispatcher("/views/admin/ticket-manage.jsp").forward(req, resp);
    }

    /**
     * 新增票品页
     */
    public void ticketAddPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;
        req.getRequestDispatcher("/views/admin/ticket-manage.jsp?action=add").forward(req, resp);
    }

    /**
     * 新增票品
     */
    public void ticketAdd(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;

        String name = req.getParameter("name");
        if (ticketService.findByName(name) != null) {
            req.setAttribute("error", "票品名称已存在");
            req.setAttribute("newTicket", buildTicketFromReq(req));
            req.setAttribute("tickets", ticketService.getAllTickets());
            req.getRequestDispatcher("/views/admin/ticket-manage.jsp?action=add").forward(req, resp);
            return;
        }

        Ticket ticket = new Ticket();
        ticket.setName(name);
        ticket.setType(req.getParameter("type"));
        ticket.setDescription(req.getParameter("description"));
        ticket.setPrice(WebUtils.getDouble(req, "price", 0));
        ticket.setStock(WebUtils.getInt(req, "stock", 0));
        ticket.setStatus("on_sale");

        String imagePath = handleImageUpload(req, "imageFile");
        if (imagePath != null) {
            ticket.setImage(imagePath);
        } else {
            ticket.setImage(req.getParameter("image"));
        }

        ticketService.addTicket(ticket);
        resp.sendRedirect(req.getContextPath() + "/admin/ticketManage");
    }

    private Ticket buildTicketFromReq(HttpServletRequest req) {
        Ticket t = new Ticket();
        t.setName(req.getParameter("name"));
        t.setType(req.getParameter("type"));
        t.setDescription(req.getParameter("description"));
        t.setPrice(WebUtils.getDouble(req, "price", 0));
        t.setStock(WebUtils.getInt(req, "stock", 0));
        t.setImage(req.getParameter("image"));
        return t;
    }

    /**
     * 编辑票品页
     */
    public void ticketEditPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;

        int id = WebUtils.getInt(req, "id", 0);
        Ticket ticket = ticketService.findById(id);
        List<Ticket> tickets = ticketService.getAllTickets();
        req.setAttribute("editTicket", ticket);
        req.setAttribute("tickets", tickets);
        req.getRequestDispatcher("/views/admin/ticket-manage.jsp?action=edit").forward(req, resp);
    }

    /**
     * 更新票品
     */
    public void ticketEdit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;

        int id = WebUtils.getInt(req, "id", 0);
        Ticket ticket = ticketService.findById(id);
        if (ticket != null) {
            ticket.setName(req.getParameter("name"));
            ticket.setType(req.getParameter("type"));
            ticket.setDescription(req.getParameter("description"));
            ticket.setPrice(WebUtils.getDouble(req, "price", 0));
            ticket.setStock(WebUtils.getInt(req, "stock", 0));
            ticket.setStatus(req.getParameter("status"));

            String imagePath = handleImageUpload(req, "imageFile");
            if (imagePath != null) {
                ticket.setImage(imagePath);
            } else if (req.getParameter("image") != null && !req.getParameter("image").isEmpty()) {
                ticket.setImage(req.getParameter("image"));
            }

            ticketService.updateTicket(ticket);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/ticketManage");
    }

    /**
     * 删除票品
     */
    public void ticketDelete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;

        int id = WebUtils.getInt(req, "id", 0);
        ticketService.deleteTicket(id);
        resp.sendRedirect(req.getContextPath() + "/admin/ticketManage");
    }

    // ===== 订单管理 =====

    /**
     * 订单管理页
     */
    public void orderManage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;

        List<Order> orders = orderService.getAllOrders();
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/admin/order-manage.jsp").forward(req, resp);
    }

    /**
     * 处理订单（标记已支付/已取消）
     */
    public void orderProcess(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!checkAdmin(req, resp)) return;

        int orderId = WebUtils.getInt(req, "id", 0);
        String status = req.getParameter("status");
        if (orderId > 0 && status != null) {
            orderService.processOrder(orderId, status);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/orderManage");
    }
}
