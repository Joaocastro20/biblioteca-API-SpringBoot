package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.api.dto.EmprestimoDTO;
import com.biblioteca.api.dto.ReturnedEmprestimoDTO;
import com.biblioteca.service.BibliotecaService;
import com.biblioteca.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("api/loans")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final BibliotecaService bibliotecaService;

    public EmprestimoController(EmprestimoService emprestimoService, BibliotecaService bibliotecaService) {
        this.emprestimoService = emprestimoService;
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody EmprestimoDTO dto){
        Book book = bibliotecaService.getByIsbn(dto.getIsbn())
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST ,"book not found"));
        Emprestimo entity = Emprestimo.builder().book(book).emprestimoDate(LocalDate.now()).customer(dto.getCustomer()).build();
    entity = emprestimoService.save(entity);
    return entity.getId();
    }

    @PatchMapping("{id}")
    public void returnedBook(@PathVariable Long id, @RequestBody ReturnedEmprestimoDTO dto){
        Emprestimo emprestimo = emprestimoService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        emprestimo.setReturned(dto.getReturned());
        emprestimoService.update(emprestimo);
    }
}
