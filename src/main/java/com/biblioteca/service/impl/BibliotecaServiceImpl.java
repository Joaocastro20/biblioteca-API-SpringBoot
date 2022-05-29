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

        return this.repository.findById(id);
    }

    @Override
    public void delete(Book book) {
        if(book == null||book.getId()==null){
            throw new IllegalArgumentException("Book id cant be null");
        }
        this.repository.delete(book);
    }

    @Override
    public Book update(Book book) {
        if(book == null||book.getId()==null){
            throw new IllegalArgumentException("Book id cant be null");
        }
        return this.repository.save(book);
    }
}
