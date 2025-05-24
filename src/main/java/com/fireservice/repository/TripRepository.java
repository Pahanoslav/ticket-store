package com.fireservice.repository;

import com.fireservice.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findAll();

    // Поиск поездок по категории (класс поезда)
    List<Trip> findByCategory(String category);

    // Поиск поездок по ID станции (станции связаны с поездками через trip_station)
    List<Trip> findByStations_Id(Long stationId);

    // Поиск поездок, где количество доступных мест больше заданного
    List<Trip> findBySeatsAvailableGreaterThan(int seatsAvailable);
}
