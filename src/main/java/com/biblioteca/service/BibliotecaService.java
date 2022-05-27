package com.biblioteca.service;


import com.biblioteca.api.domain.Book;

import java.util.Optional;


public interface BibliotecaService {
    Book save(Book any);

    Optional<Book> getById(Long id);
}
