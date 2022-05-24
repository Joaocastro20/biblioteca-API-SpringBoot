package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.dto.BookDTO;
import com.biblioteca.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BibliotecaController {


    private BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService){
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO dto){
        Book build = Book.builder().author(dto.getAuthor()).title(dto.getTitle()).isbn(dto.getIsbn()).build();
        build = bibliotecaService.save(build);
        return BookDTO.builder().id(build.getId()).author(dto.getAuthor()).title(dto.getTitle()).isbn(dto.getIsbn()).build();
    }
}
