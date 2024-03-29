package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.domain.Emprestimo;
import com.biblioteca.api.dto.EmprestimoDTO;
import com.biblioteca.api.dto.EmprestimoFilterDTO;
import com.biblioteca.api.dto.ReturnedEmprestimoDTO;
import com.biblioteca.exceptions.BusinessException;
import com.biblioteca.service.BibliotecaService;
import com.biblioteca.service.EmprestimoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.biblioteca.api.resource.BibliotecaControllerTest.BOOK_API;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = EmprestimoController.class)
@AutoConfigureMockMvc
public class EmprestimoControllerTest {

    static final String LOAN_API = "/api/loans";

    @Autowired
    MockMvc mvc;

    @MockBean
    private BibliotecaService bibliotecaService;

    @MockBean
    private EmprestimoService emprestimoService;

    @Test
    @DisplayName("Deve realizar um emprestimo")
    public void createEmprestimoTeste() throws Exception{
        EmprestimoDTO dto = EmprestimoDTO.builder().isbn("123").email("teste@email.com").customer("Fulano").build();
        String json = new ObjectMapper().writeValueAsString(dto);

        Book book = Book.builder().id(1L).isbn("123").build();

        BDDMockito.given(bibliotecaService.getByIsbn("123"))
                .willReturn(Optional.of(book));

        Emprestimo emprestimo = Emprestimo
                .builder().id(1l).customer("Fulano").book(book).emprestimoDate(LocalDate.now()).build();

        BDDMockito.given(emprestimoService.save(Mockito.any(Emprestimo.class))).willReturn(emprestimo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(LOAN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("Deve lançar erro com isbn inexistente")
    public void invalidIsbnCreate() throws Exception {
        EmprestimoDTO dto = EmprestimoDTO.builder().isbn("123").customer("Fulano").build();
        String json = new ObjectMapper().writeValueAsString(dto);
        System.out.println(json);

        BDDMockito.given(bibliotecaService.getByIsbn("123"))
                .willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(LOAN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("book not found"))
        ;
    }

    @Test
    @DisplayName("deve lancar erro ao tenta empresta livro ja emprestado")
    public void loanErrorCreate() throws Exception{
        EmprestimoDTO dto = EmprestimoDTO.builder().isbn("123").customer("Fulano").build();
        String json = new ObjectMapper().writeValueAsString(dto);
        System.out.println(json);

        Book book = Book.builder().id(1L).isbn("123").build();

        BDDMockito.given(bibliotecaService.getByIsbn("123"))
                .willReturn(Optional.of(book));

        BDDMockito.given(emprestimoService.save(Mockito.any(Emprestimo.class)))
                .willThrow(new BusinessException("Book already loaned"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(LOAN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Book already loaned"))
        ;
    }

    @Test
    @DisplayName("Deve retornar um livro")
    public void returnBookTest() throws Exception {
        ReturnedEmprestimoDTO dto = ReturnedEmprestimoDTO.builder().returned(true).build();
        Emprestimo emprestimo = Emprestimo.builder().id(1L).build();
        BDDMockito.given(emprestimoService.getById(Mockito.anyLong())).willReturn(Optional.of(
                emprestimo
        ));


        String json = new ObjectMapper().writeValueAsString(dto);

        mvc.perform(
                patch(LOAN_API.concat("/1"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());

        Mockito.verify(emprestimoService, Mockito.times(1)).update(emprestimo);
    }

    @Test
    @DisplayName("Deve retornar 404 ao devolver um livro existente")
    public void returnFoundBookTest() throws Exception {
        ReturnedEmprestimoDTO dto = ReturnedEmprestimoDTO.builder().returned(true).build();
        Emprestimo emprestimo = Emprestimo.builder().id(1L).build();
        BDDMockito.given(emprestimoService.getById(Mockito.anyLong())).willReturn(Optional.empty()
        );


        String json = new ObjectMapper().writeValueAsString(dto);

        mvc.perform(
                patch(LOAN_API.concat("/1"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve filtrar emprestimos")
    public void findEmprestimoTest() throws Exception {
        Long id = 1l;
        Book book = Book.builder().id(1L).isbn("123").build();
        Emprestimo emprestimo = Emprestimo.builder()
                .book(book)
                .build();

        BDDMockito.given(emprestimoService.find(Mockito.any(EmprestimoFilterDTO.class),Mockito.any(Pageable.class)))
                .willReturn(new PageImpl<Emprestimo>(Arrays.asList(emprestimo), PageRequest.of(0,10),1));

        String queryString = String.format("?isbn=%s&customer=%s&page=0&size=10",book.getIsbn(),emprestimo.getCustomer());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(LOAN_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content",Matchers.hasSize(1)))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(10))
                .andExpect(jsonPath("pageable.pageNumber").value(0))
        ;

    }
}
