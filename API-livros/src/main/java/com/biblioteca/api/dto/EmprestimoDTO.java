package com.biblioteca.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {
    private String isbn;
    private String customer;
}
