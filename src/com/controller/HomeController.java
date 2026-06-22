package com.controller;

import com.model.entity.Ticket;
import com.model.service.TicketService;
import com.model.service.impl.TicketServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 首页控制器
 */
public class HomeController extends BaseServlet {

    private TicketService ticketService = new TicketServiceImpl();

    /**
     * 首页 - 展示在售票品（支持按类型筛选）
     */
    public void index(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String type = req.getParameter("type");
        List<Ticket> ticketList;

        if (type != null && !type.trim().isEmpty()) {
            ticketList = ticketService.getByType(type);
            req.setAttribute("currentType", type);
        } else {
            ticketList = ticketService.getOnSaleList();
            Map<String, List<Ticket>> grouped = ticketList.stream()
                    .collect(Collectors.groupingBy(Ticket::getType, LinkedHashMap::new, Collectors.toList()));
            req.setAttribute("groupedTickets", grouped);
            List<String> typeOrder = Arrays.asList("concert", "体育", "电影", "演出");
            req.setAttribute("typeOrder", typeOrder);
        }

        req.setAttribute("ticketList", ticketList);
        req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
    }
}
