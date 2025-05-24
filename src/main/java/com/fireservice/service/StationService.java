package com.fireservice.service;

import com.fireservice.model.Station;
import com.fireservice.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElse(null);
    }

    public void saveStation(Station station) {
        stationRepository.save(station);
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    public List<Station> getStationsByIds(List<Long> ids) {
        return stationRepository.findAllById(ids);
    }
}
