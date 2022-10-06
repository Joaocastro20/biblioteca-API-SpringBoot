package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.api.dto.BookDTO;
import com.biblioteca.api.dto.EmprestimoDTO;
import com.biblioteca.api.dto.EmprestimoFilterDTO;
import com.biblioteca.api.dto.ReturnedEmprestimoDTO;
import com.biblioteca.service.BibliotecaService;
import com.biblioteca.service.EmprestimoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("api/loans")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final BibliotecaService bibliotecaService;
    private final ModelMapper modelMapper;

    public EmprestimoController(EmprestimoService emprestimoService, BibliotecaService bibliotecaService, ModelMapper modelMapper) {
        this.emprestimoService = emprestimoService;
        this.bibliotecaService = bibliotecaService;
        this.modelMapper = modelMapper;
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

    @GetMapping
    public Page<EmprestimoDTO> find(EmprestimoFilterDTO dto, Pageable pageable){
        Page<Emprestimo> result = emprestimoService.find(dto,pageable);
        List<EmprestimoDTO> loans = result
                .getContent()
                .stream()
                .map(
                        entity -> {
                            Book book = entity.getBook();
                            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
                           EmprestimoDTO emprestimoDTO = modelMapper.map(entity,EmprestimoDTO.class);
                           emprestimoDTO.setBook(bookDTO);
                           return emprestimoDTO;
                        }

                ).collect(Collectors.toList());

        return new PageImpl<EmprestimoDTO>(loans,pageable,result.getTotalElements());
    }
}
