package com.biblioteca.service;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
}
