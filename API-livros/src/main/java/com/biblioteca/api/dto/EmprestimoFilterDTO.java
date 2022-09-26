package com.biblioteca.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoFilterDTO {
    private String isbn;
    private String customer;
}
