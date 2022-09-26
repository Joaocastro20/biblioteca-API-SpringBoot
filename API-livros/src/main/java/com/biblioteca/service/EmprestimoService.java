package com.biblioteca.service;

import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.api.dto.EmprestimoFilterDTO;
import com.biblioteca.api.resource.BibliotecaController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmprestimoService {
    Emprestimo save(Emprestimo emprestimo);

    Optional<Emprestimo> getById(Long id);

    Emprestimo update(Emprestimo emprestimo);

    Page<Emprestimo> find(EmprestimoFilterDTO filterDTO, Pageable pageable);
}
