package com.biblioteca.service;


import com.biblioteca.api.domain.Book;
import org.springframework.stereotype.Service;

@Service
public interface BibliotecaService {
    Book save(Book any);
}
