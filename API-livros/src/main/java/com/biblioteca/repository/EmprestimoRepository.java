package com.biblioteca.repository;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo,Long> {
    boolean existsByBookAndNotReturned(Book book);
}
