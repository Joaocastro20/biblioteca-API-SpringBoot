package com.biblioteca.repository;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo,Long> {

    @Query(value = "select case when ( count (l.id)>0) then " +
            "true else false end from Emprestimo l where  l.book =:book and (l.returned is null or l.returned is false)")
    boolean existsByBookAndNotReturned(@Param("book") Book book);

    @Query(value = "select l from Emprestimo as l join l.book as b where b.isbn = :isbn or l.customer =:customer")
    Page<Emprestimo> findByBookIsbnOrCustomer(@Param("isbn") String isbn,
                                              @Param("customer") String customer,
                                              Pageable pageable);

    Page<Emprestimo> findByBook(Book book, Pageable pageable);

    @Query(" select l from Emprestimo l where l.emprestimoDate <= :threeDaysAgo and (l.returned is null or l.returned is false)")
    List<Emprestimo> findByEmprestimoDateLessThanAndNotReturned(@Param("threeDaysAgo") LocalDate threeDaysAgo);
}
