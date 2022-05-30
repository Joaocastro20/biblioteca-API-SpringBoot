package com.biblioteca.service;

import com.biblioteca.api.domain.Book;
import com.biblioteca.repository.BibliotecaRepository;
import com.biblioteca.service.impl.BibliotecaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BibliotecaServiceTest {

    BibliotecaService bibliotecaService;

    @MockBean
    BibliotecaRepository repository;

    @BeforeEach
    public void setUp(){
        this.bibliotecaService = new BibliotecaServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest(){
        Book book = Book.builder().author("teste").title("testando").isbn("125").build();

        Mockito.when(repository.save(book)).thenReturn(Book.builder().id(1l).isbn("125").title("testando").author("teste").build());

        Book bo = bibliotecaService.save(book);

        assertThat(bo.getId()).isNotNull();
        assertThat(bo.getIsbn()).isEqualTo("125");
        assertThat(bo.getAuthor()).isEqualTo("teste");
        assertThat(bo.getTitle()).isEqualTo("testando");
    }

    private Book createBook(){
        return Book.builder().isbn("123").author("Fulano").title("As Aventuras").build();
    }

    @Test
    @DisplayName("Deve obter um book por id na camada de servico")
    public void getById(){
        Long id = 1l;
        Book book = createBook();
        book.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bibliotecaService.getById(id);

        assertThat(foundBook.isPresent()).isTrue();
        assertThat(foundBook.get().getId()).isEqualTo(id);
        assertThat(foundBook.get().getIsbn()).isEqualTo(book.getIsbn());
        assertThat(foundBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(foundBook.get().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    @DisplayName("Deve deletar um book")
    public void deletBook(){
        Book book = Book.builder().id(1l).build();

        Assertions.assertDoesNotThrow(() ->bibliotecaService.delete(book));

        Mockito.verify(repository,Mockito.times(1)).delete(book);


    }
    @Test
    @DisplayName("Deve ocorrer erro ao deletar um book")
    public void deletInvalidBook(){
        Book book = new Book();

        Assertions.assertThrows(IllegalArgumentException.class,() ->bibliotecaService.delete(book));

        Mockito.verify(repository,Mockito.never()).delete(book);


    }
    @Test
    @DisplayName("Deve atualizar um livro")
    public void UpdateBook(){
        long id = 1l;

        Book updatingBook = Book.builder().id(id).build();

        Book updateBook = createBook();
        updateBook.setId(id);
        Mockito.when(repository.save(updatingBook)).thenReturn(updateBook);

        Book book = bibliotecaService.update(updatingBook);

        assertThat(book.getId()).isEqualTo(updateBook.getId());
        assertThat(book.getTitle()).isEqualTo(updateBook.getTitle());
        assertThat(book.getAuthor()).isEqualTo(updateBook.getAuthor());
        assertThat(book.getIsbn()).isEqualTo(updateBook.getIsbn());
    }

    @Test
    @DisplayName("Deve filtrar books pelas propiedades")
    public void findBookTest(){
        Book book = createBook();

        PageRequest pageRequest = PageRequest.of(0,10);

        List<Book> list = Arrays.asList(book);
        Page<Book> page = new PageImpl<Book>(list, PageRequest.of(0,10),1);
        Mockito.when(repository.findAll(Mockito.any(Example.class),Mockito.any(PageRequest.class)))
            .thenReturn(page);

        Page<Book> result =  bibliotecaService.find(book,pageRequest);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(list);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);

    }
}
