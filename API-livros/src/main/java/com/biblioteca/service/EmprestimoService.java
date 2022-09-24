package com.biblioteca.service;

import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.api.resource.BibliotecaController;

import java.util.Optional;

public interface EmprestimoService {
    Emprestimo save(Emprestimo emprestimo);

    Optional<Emprestimo> getById(Long id);

    Emprestimo update(Emprestimo emprestimo);
}
