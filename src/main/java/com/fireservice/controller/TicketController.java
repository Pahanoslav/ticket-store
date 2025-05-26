package com.fireservice.controller;

import com.fireservice.model.Ticket;
import com.fireservice.model.Trip;
import com.fireservice.model.User;
import com.fireservice.service.CustomUserDetails;
import com.fireservice.service.TicketService;
import com.fireservice.service.TripService;
import com.fireservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String getAllTickets(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<Ticket> tickets;

        if (isAdmin) {
            tickets = ticketService.getAllTickets();  // все билеты для админа
        } else {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User currentUser = customUserDetails.getUser();
            tickets = ticketService.getTicketsByUserId(currentUser.getId());  // билеты только текущего пользователя
        }

        tickets.forEach(t -> {
            if (t.getPurchaseDate() != null) {
                t.setPurchaseDateString(t.getPurchaseDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }
        });

        model.addAttribute("tickets", tickets);
        return "tickets/list";
    }

//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//        model.addAttribute("ticket", new Ticket());
//        model.addAttribute("trips", tripService.getAllTrips());
//        model.addAttribute("users", userService.getAllUsers());
//        return "tickets/add";
//    }

    @GetMapping("/add")
    public String showAddForm(Model model, Authentication authentication) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("trips", tripService.getAllTrips());

        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<User> users;
        if (isAdmin) {
            System.out.println("ISADMIN IS: " + isAdmin);
            users = userService.getAllUsers();  // все пользователи для админа
        } else {
            System.out.println("ISADMIN IS: " + isAdmin);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User currentUser = customUserDetails.getUser();
            users = List.of(currentUser);  // только текущий пользователь для обычного юзера
        }
        model.addAttribute("users", users);

        return "orders/add";
    }

    @PostMapping("/add")
    public String addTicket(@ModelAttribute Ticket ticket, Model model) {
        Trip trip = tripService.getTripById(ticket.getTrip().getId());
        int seatsLeft = trip.getSeatsAvailable();
        int quantity = ticket.getQuantity();

        if (quantity > seatsLeft) {
            model.addAttribute("error", "Недостаточно доступных мест для выбранного рейса");
            model.addAttribute("ticket", ticket);
            model.addAttribute("trips", tripService.getAllTrips());

            // Можно добавить пользователей, если в форме нужен выбор
            // model.addAttribute("users", userService.getAllUsers());

            return "tickets/add"; // возвращаем форму с ошибкой
        }

        trip.setSeatsAvailable(seatsLeft - quantity);
        tripService.saveTrip(trip);

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
