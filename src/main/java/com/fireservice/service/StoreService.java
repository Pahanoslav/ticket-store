package com.fireservice.service;

import com.fireservice.model.Station;
import com.fireservice.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Station> getAllStores() {
        return storeRepository.findAll();
    }

    public Station getStoreById(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    public void saveStore(Station station) {
        storeRepository.save(station);
    }

    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    public List<Station> getStoresByIds(List<Long> ids) {
        return storeRepository.findAllById(ids);
    }
}
