package com.fireservice.repository;

import com.fireservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAll();

    List<Ticket> findByUserId(Long userId);

    List<Ticket> findByStatus(String status);
}
