package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.dto.BookDTO;
import com.biblioteca.api.exceptions.ApiErros;
import com.biblioteca.service.BibliotecaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BibliotecaController {


    private BibliotecaService bibliotecaService;
    private ModelMapper modelMapper;

    public BibliotecaController(BibliotecaService bibliotecaService, ModelMapper modelMapper){
        this.bibliotecaService = bibliotecaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto){
        Book build = modelMapper.map(dto, Book.class);
        build = bibliotecaService.save(build);
        return modelMapper.map(build, BookDTO.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleValidException(MethodArgumentNotValidException ex){
        BindingResult bindingResult =  ex.getBindingResult();
        return new ApiErros(bindingResult);
    }
}
