package com.model.service;

import com.model.entity.Ticket;
import java.util.List;

public interface TicketService {
    /** 获取所有在售票品 */
    List<Ticket> getOnSaleList();
    /** 根据类型获取 */
    List<Ticket> getByType(String type);
    /** 搜索 */
    List<Ticket> search(String keyword);
    /** 根据ID查询 */
    Ticket findById(int id);
    /** 根据名称查询 */
    Ticket findByName(String name);
    /** 新增票品（管理员） */
    boolean addTicket(Ticket ticket);
    /** 更新票品（管理员） */
    boolean updateTicket(Ticket ticket);
    /** 删除票品（管理员） */
    boolean deleteTicket(int id);
    /** 获取所有票品（管理员） */
    List<Ticket> getAllTickets();
    /** 按名称搜索所有票品（管理员） */
    List<Ticket> searchAllTickets(String keyword);
}
