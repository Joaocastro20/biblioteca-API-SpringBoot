package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.dto.BookDTO;
import com.biblioteca.api.exceptions.ApiErros;
import com.biblioteca.exceptions.BusinessException;
import com.biblioteca.service.BibliotecaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("{id}")
    public BookDTO get(@PathVariable Long id){
        return bibliotecaService
            .getById(id)
            .map(book -> modelMapper.map(book,BookDTO.class))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        Book book = bibliotecaService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bibliotecaService.delete(book);
    }

    @PutMapping("{id}")
    public BookDTO update(@PathVariable Long id, BookDTO dto){
        return bibliotecaService.getById(id).map(book1 -> {
            book1.setAuthor(dto.getAuthor());
            book1.setTitle(dto.getTitle());
            book1 = bibliotecaService.update(book1);
            return modelMapper.map(book1,BookDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping
    public Page<BookDTO> find (BookDTO dto, Pageable pageRequest){
        Book filter = modelMapper.map(dto, Book.class);
        Page<Book> result = bibliotecaService.find(filter,pageRequest);
        List<BookDTO> list = result.getContent()
            .stream()
            .map(entity -> modelMapper.map(entity,BookDTO.class))
            .collect(Collectors.toList());
        return  new PageImpl<BookDTO>(list,pageRequest,result.getTotalElements());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleValidException(MethodArgumentNotValidException ex){
        BindingResult bindingResult =  ex.getBindingResult();
        return new ApiErros(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handlerBusinessException(BusinessException ex){
        return new ApiErros(ex);
    }
}
