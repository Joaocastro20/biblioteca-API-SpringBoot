package com.biblioteca.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Emprestimo {

    private Long id;
    private String customer;
    private Book book;
    private LocalDate emprestimoDate;
    private Boolean returned;

}
