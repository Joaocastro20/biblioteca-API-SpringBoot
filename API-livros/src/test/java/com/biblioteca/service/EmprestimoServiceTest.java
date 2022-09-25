package com.biblioteca.service;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.exceptions.BusinessException;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.service.impl.EmprestimoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EmprestimoServiceTest {

    @MockBean
    private EmprestimoRepository repository;

    private EmprestimoService emprestimoService;

    @BeforeEach
    public void setUp(){
        this.emprestimoService = new EmprestimoServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um emprestimo")
    public void saveLoanTest(){
        Book book = Book.builder().id(1l).build();
        String custumer = "Fulano";
        Emprestimo emprestimo = Emprestimo.builder()
                .book(book)
                .customer(custumer)
                .emprestimoDate(LocalDate.now())
                .build();
        Emprestimo savedEmprestimo = Emprestimo.builder()
                .id(1l)
                .emprestimoDate(LocalDate.now())
                .customer(custumer)
                .book(book)
                .build();


        when(repository.save(emprestimo)).thenReturn(savedEmprestimo);

        Emprestimo saving = emprestimoService.save(emprestimo);

        assertThat(saving.getId()).isEqualTo(savedEmprestimo.getId());
        assertThat(saving.getCustomer()).isEqualTo(savedEmprestimo.getCustomer());
        assertThat(saving.getEmprestimoDate()).isEqualTo(savedEmprestimo.getEmprestimoDate());
        assertThat(saving.getBook().getId()).isEqualTo(savedEmprestimo.getBook().getId());
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar salvar emprestimo com livro ja emprestado")
    public void invalidLoanBookSaved(){
        Book book = Book.builder().id(1l).build();
        String custumer = "Fulano";
        Emprestimo savedEmprestimo = Emprestimo.builder()
                .id(1l)
                .emprestimoDate(LocalDate.now())
                .customer(custumer)
                .book(book)
                .build();

        when(repository.existsByBookAndNotReturned(book)).thenReturn(true);

        Throwable exception =  catchThrowable(()->emprestimoService.save(savedEmprestimo));

        assertThat(exception).isInstanceOf(BusinessException.class)
                .hasMessage("Book already loaned");

        verify(repository, never()).save(savedEmprestimo);

    }

    @Test
    @DisplayName("Deve obter as informaçoes de um emprestimo pelo ID")
    public void getByIdLoan(){
        Emprestimo emprestimo = Emprestimo.builder().id(1L).build();
        Book book = Book.builder().id(1l).build();
        Emprestimo savedEmprestimo = Emprestimo.builder()
                .id(1l)
                .emprestimoDate(LocalDate.now())
                .customer("fulano")
                .book(book)
                .build();
        when(repository.findById(emprestimo.getId())).thenReturn(Optional.of(savedEmprestimo));

        Optional<Emprestimo> result = emprestimoService.getById(emprestimo.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(savedEmprestimo.getId());
        assertThat(result.get().getCustomer()).isEqualTo(savedEmprestimo.getCustomer());
        assertThat(result.get().getBook()).isEqualTo(savedEmprestimo.getBook());
        assertThat(result.get().getEmprestimoDate()).isEqualTo(savedEmprestimo.getEmprestimoDate());

        verify(repository).findById(savedEmprestimo.getId());
    }

    @Test
    @DisplayName("Deve atualizar as informaçoes de um emprestimo")
    public void updateLoan(){
        Emprestimo emprestimo = Emprestimo.builder().id(1L).build();
        Book book = Book.builder().id(1l).build();
        Emprestimo savedEmprestimo = Emprestimo.builder()
                .id(1l)
                .returned(true)
                .emprestimoDate(LocalDate.now())
                .customer("fulano")
                .book(book)
                .build();
        when(repository.save(savedEmprestimo)).thenReturn(savedEmprestimo);

        Emprestimo result = emprestimoService.update(savedEmprestimo);

        assertThat(result.getReturned()).isTrue();

        verify(repository).save(savedEmprestimo);
    }
}
