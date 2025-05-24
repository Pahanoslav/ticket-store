package com.fireservice.controller;

import com.fireservice.model.Ticket;
import com.fireservice.service.TicketService;
import com.fireservice.service.TripService;
import com.fireservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TripService tripService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllTickets(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();
        tickets.forEach(t -> {
            if (t.getPurchaseDate() != null) {
                t.setPurchaseDateString(t.getPurchaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }
        });
        model.addAttribute("tickets", tickets);
        return "tickets/list";  // Thymeleaf шаблон tickets/list.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("trips", tripService.getAllTrips());
        model.addAttribute("users", userService.getAllUsers());
        return "tickets/add";
    }

    @PostMapping("/add")
    public String addTicket(@ModelAttribute Ticket ticket) {
        ticketService.saveTicket(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("trips", tripService.getAllTrips());
        model.addAttribute("users", userService.getAllUsers());
        return "tickets/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTicket(@PathVariable Long id, @ModelAttribute Ticket ticket) {
        ticket.setId(id);
        ticketService.saveTicket(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/tickets";
    }
}
