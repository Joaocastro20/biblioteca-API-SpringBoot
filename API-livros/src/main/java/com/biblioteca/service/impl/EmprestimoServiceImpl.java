package com.biblioteca.service.impl;

import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.exceptions.BusinessException;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.service.EmprestimoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    private EmprestimoRepository emprestimoRepository;

    public EmprestimoServiceImpl(EmprestimoRepository repository) {
        this.emprestimoRepository = repository;
    }

    @Override
    public Emprestimo save(Emprestimo emprestimo) {
        if(emprestimoRepository.existsByBookAndNotReturned(emprestimo.getBook())){
            throw new BusinessException("Book already loaned");
        }
        return emprestimoRepository.save(emprestimo);
    }

    @Override
    public Optional<Emprestimo> getById(Long id) {
        return emprestimoRepository.findById(id);
    }

    @Override
    public Emprestimo update(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }
}
