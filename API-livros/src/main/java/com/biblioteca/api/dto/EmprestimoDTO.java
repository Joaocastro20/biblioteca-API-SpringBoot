package com.biblioteca.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {

    private Long id;
    private String isbn;
    private String customer;
    private String email;
    private BookDTO book;

}
