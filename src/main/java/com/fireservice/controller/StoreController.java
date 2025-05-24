package com.fireservice.controller;

import com.fireservice.model.Station;
import com.fireservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public String getAllStores(Model model) {
        model.addAttribute("stores", storeService.getAllStores());
        return "stores/list"; // Thymeleaf шаблон stores/list.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("store", new Station());
        return "stores/add";
    }

    @PostMapping("/add")
    public String addStore(@ModelAttribute Station station) {
        storeService.saveStore(station);
        return "redirect:/stores";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Station station = storeService.getStoreById(id);
        model.addAttribute("store", station);
        return "stores/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateStore(@PathVariable Long id, @ModelAttribute Station station) {
        station.setId(id);
        storeService.saveStore(station);
        return "redirect:/stores";
    }

    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return "redirect:/stores";
    }
}
