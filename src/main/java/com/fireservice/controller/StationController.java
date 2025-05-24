package com.fireservice.controller;

import com.fireservice.model.Station;
import com.fireservice.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping
    public String getAllStations(Model model) {
        model.addAttribute("stations", stationService.getAllStations());
        return "stations/list"; // Thymeleaf шаблон stations/list.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("station", new Station());
        return "stations/add";
    }

    @PostMapping("/add")
    public String addStation(@ModelAttribute Station station) {
        stationService.saveStation(station);
        return "redirect:/stations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Station station = stationService.getStationById(id);
        model.addAttribute("station", station);
        return "stations/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateStation(@PathVariable Long id, @ModelAttribute Station station) {
        station.setId(id);
        stationService.saveStation(station);
        return "redirect:/stations";
    }

    @GetMapping("/delete/{id}")
    public String deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return "redirect:/stations";
    }
}
