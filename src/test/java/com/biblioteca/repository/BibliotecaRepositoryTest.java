package com.biblioteca.repository;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.dto.BookDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BibliotecaRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BibliotecaRepository bibliotecaRepository;


    @Test
    @DisplayName("Deve retornar verdadeiro quando houver o isbn informado")
    public void verificaIsbn(){
        String isbn = "124";
        Book book = Book.builder().title("teste").author("teste").isbn(isbn).build();
        entityManager.persist(book);

        boolean exists = bibliotecaRepository.existsByIsbn(isbn);

        Assertions.assertThat(exists).isTrue();
    }

}
