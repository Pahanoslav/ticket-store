package com.fireservice.service;

import com.fireservice.model.Ticket;
import com.fireservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        System.out.println("Tickets count: " + tickets.size());
        tickets.forEach(ticket -> {
            System.out.println("Ticket ID: " + ticket.getId());
            if (ticket.getUser() != null) {
                System.out.println("  User ID: " + ticket.getUser().getId() + ", username: " + ticket.getUser().getUsername());
            } else {
                System.out.println("  User is null!");
            }
            if (ticket.getTrip() != null) {
                System.out.println("  Trip ID: " + ticket.getTrip().getId() + ", title: " + ticket.getTrip().getTitle());
            } else {
                System.out.println("  Trip is null!");
            }
            System.out.println("  Quantity: " + ticket.getQuantity());
            System.out.println("  Status: " + ticket.getStatus());
            System.out.println("  Purchase Date: " + ticket.getPurchaseDate());
        });
        return tickets;
    }

    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
