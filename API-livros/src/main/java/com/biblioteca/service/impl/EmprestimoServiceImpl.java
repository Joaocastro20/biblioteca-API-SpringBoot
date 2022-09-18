package com.biblioteca.service.impl;

import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.service.EmprestimoService;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    private EmprestimoRepository emprestimoRepository;

    public EmprestimoServiceImpl(EmprestimoRepository repository) {
        this.emprestimoRepository = repository;
    }

    @Override
    public Emprestimo save(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }
}
