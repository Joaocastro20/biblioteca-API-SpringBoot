package com.biblioteca.service.impl;

import com.biblioteca.api.domain.Book;
import com.biblioteca.repository.BibliotecaRepository;
import com.biblioteca.service.BibliotecaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BibliotecaServiceImpl implements BibliotecaService {

    private BibliotecaRepository repository;

    public BibliotecaServiceImpl(BibliotecaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return null;
    }
}
