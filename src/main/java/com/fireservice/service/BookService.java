package com.fireservice.service;

import com.fireservice.model.Trip;
import com.fireservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Trip> getAllBooks() {
        return bookRepository.findAll();
    }

    public Trip getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void saveBook(Trip trip) {
        bookRepository.save(trip);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
