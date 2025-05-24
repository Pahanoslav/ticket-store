package com.fireservice.repository;

import com.fireservice.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAll();

    List<Trip> findByCategory(String category);

    List<Trip> findByStores_Id(Long storeId);

    List<Trip> findByStockGreaterThan(int stock);
}
