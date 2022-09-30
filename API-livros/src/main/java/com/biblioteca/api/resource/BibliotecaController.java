package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.api.dto.BookDTO;
import com.biblioteca.api.dto.EmprestimoDTO;
import com.biblioteca.api.exceptions.ApiErros;
import com.biblioteca.exceptions.BusinessException;
import com.biblioteca.service.BibliotecaService;
import com.biblioteca.service.EmprestimoService;
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
    private EmprestimoService emprestimoService;

    public BibliotecaController(BibliotecaService bibliotecaService, ModelMapper modelMapper,EmprestimoService emprestimoService){
        this.bibliotecaService = bibliotecaService;
        this.modelMapper = modelMapper;
        this.emprestimoService = emprestimoService;
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


    @GetMapping("{id}/loans")
    public Page<EmprestimoDTO> emprestimoByBook(@PathVariable Long id, Pageable pageable){
        Book book = bibliotecaService.getById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Page<Emprestimo> result = emprestimoService.getEmprestimoByBook(book,pageable);
        List<EmprestimoDTO> list =  result.getContent()
                .stream()
                .map(
                        emprestimo -> {
                            Book emprestimoBook = emprestimo.getBook();
                            BookDTO bookDTO = modelMapper.map(emprestimoBook,BookDTO.class);
                            EmprestimoDTO emprestimoDTO = modelMapper.map(emprestimo,EmprestimoDTO.class);
                            emprestimoDTO.setBook(bookDTO);
                            return emprestimoDTO;
                        }
                ).collect(Collectors.toList());
        return new PageImpl<EmprestimoDTO>(list,pageable,result.getTotalElements());
    }
}
