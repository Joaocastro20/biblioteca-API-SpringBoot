package com.biblioteca.repository;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static com.biblioteca.repository.BibliotecaRepositoryTest.createNewBook;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class EmprestimoRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    EmprestimoRepository repository;


    @Test
    @DisplayName("Deve retornar true quando book estiver emprestado")
    public void existsByBookAndNotReturnedTest(){
        Book book = createNewBook("123");

        entityManager.persist(book);

        Emprestimo emprestimo = Emprestimo.builder()
                .book(book).customer("Fulano").emprestimoDate(LocalDate.now()).build();

        entityManager.persist(emprestimo);

        boolean exists = repository.existsByBookAndNotReturned(book);

        assertThat(exists).isTrue();
    }
}