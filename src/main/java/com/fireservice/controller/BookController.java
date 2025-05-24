package com.fireservice.controller;

import com.fireservice.model.Station;
import com.fireservice.model.Trip;
import com.fireservice.service.BookService;
import com.fireservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private StoreService storeService;

    // Список всех книг
    @GetMapping
    public String listBooks(Model model) {
        List<Trip> trips = bookService.getAllBooks();
        model.addAttribute("books", trips);
        return "books/list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Trip());
        model.addAttribute("stores", storeService.getAllStores());
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Trip trip, @RequestParam(required = false) List<Long> storeIds) {
        if (storeIds != null) {
            Set<Station> stations = new HashSet<>(storeService.getStoresByIds(storeIds));
            trip.setStores(stations);
        } else {
            trip.setStores(new HashSet<>());
        }
        bookService.saveBook(trip);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Trip trip = bookService.getBookById(id);
        model.addAttribute("book", trip);
        model.addAttribute("stores", storeService.getAllStores()); // список магазинов для выбора
        return "books/edit"; // имя шаблона Thymeleaf для редактирования
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Trip trip, @RequestParam(required = false) List<Long> storeIds) {
        trip.setId(id);
        if (storeIds != null) {
            Set<Station> stations = new HashSet<>(storeService.getStoresByIds(storeIds));
            trip.setStores(stations);
        }
        bookService.saveBook(trip);
        return "redirect:/books";
    }
}
