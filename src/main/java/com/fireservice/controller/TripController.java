package com.fireservice.controller;

import com.fireservice.model.Station;
import com.fireservice.model.Trip;
import com.fireservice.service.StationService;
import com.fireservice.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private StationService stationService;

    // Список всех поездок
    @GetMapping
    public String listTrips(Model model) {
        List<Trip> trips = tripService.getAllTrips();
        model.addAttribute("trips", trips);
        return "trips/list";
    }

    @GetMapping("/add")
    public String showAddTripForm(Model model) {
        model.addAttribute("trip", new Trip());
        model.addAttribute("stations", stationService.getAllStations());
        return "trips/add";
    }

    @PostMapping("/add")
    public String addTrip(@ModelAttribute Trip trip, @RequestParam(required = false) List<Long> stationIds) {
        if (stationIds != null) {
            Set<Station> stations = new HashSet<>(stationService.getStationsByIds(stationIds));
            trip.setStations(stations);
        } else {
            trip.setStations(new HashSet<>());
        }
        tripService.saveTrip(trip);
        return "redirect:/trips";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Trip trip = tripService.getTripById(id);
        model.addAttribute("trip", trip);
        model.addAttribute("stations", stationService.getAllStations());
        return "trips/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTrip(@PathVariable Long id, @ModelAttribute Trip trip, @RequestParam(required = false) List<Long> stationIds) {
        trip.setId(id);
        if (stationIds != null) {
            Set<Station> stations = new HashSet<>(stationService.getStationsByIds(stationIds));
            trip.setStations(stations);
        }
        tripService.saveTrip(trip);
        return "redirect:/trips";
    }
}
