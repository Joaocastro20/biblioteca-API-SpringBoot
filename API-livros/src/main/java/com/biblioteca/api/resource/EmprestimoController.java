package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.api.dto.EmprestimoDTO;
import com.biblioteca.service.BibliotecaService;
import com.biblioteca.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/loans")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final BibliotecaService bibliotecaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody EmprestimoDTO dto){
        Book book = bibliotecaService.getByIsbn(dto.getIsbn()).get();
        Emprestimo entity = Emprestimo.builder().book(book).emprestimoDate(LocalDate.now()).customer(dto.getCustomer()).build();
    entity = emprestimoService.save(entity);
    return entity.getId();
    }
}
