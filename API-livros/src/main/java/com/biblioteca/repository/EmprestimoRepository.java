package com.biblioteca.repository;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmprestimoRepository extends JpaRepository<Emprestimo,Long> {

    @Query(value = "select case when ( count (l.id)>0) then " +
            "true else false end from Emprestimo l where  l.book =:book and (l.returned is null or l.returned is false)")
    boolean existsByBookAndNotReturned(@Param("book") Book book);
}
