package com.model.service.impl;

import com.model.dao.TicketDao;
import com.model.entity.Ticket;
import com.model.service.TicketService;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao = new TicketDao();

    @Override
    public List<Ticket> getOnSaleList() {
        return ticketDao.findAllOnSale();
    }

    @Override
    public List<Ticket> getByType(String type) {
        return ticketDao.findByType(type);
    }

    @Override
    public List<Ticket> search(String keyword) {
        return ticketDao.search(keyword);
    }

    @Override
    public Ticket findById(int id) {
        return ticketDao.findById(id);
    }

    @Override
    public Ticket findByName(String name) {
        return ticketDao.findByName(name);
    }

    @Override
    public boolean addTicket(Ticket ticket) {
        return ticketDao.insert(ticket) > 0;
    }

    @Override
    public boolean updateTicket(Ticket ticket) {
        return ticketDao.update(ticket) > 0;
    }

    @Override
    public boolean deleteTicket(int id) {
        return ticketDao.delete(id) > 0;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }

    @Override
    public List<Ticket> searchAllTickets(String keyword) {
        return ticketDao.searchAll(keyword);
    }
}
