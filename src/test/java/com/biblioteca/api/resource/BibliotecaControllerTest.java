package com.biblioteca.api.resource;

import com.biblioteca.api.domain.Book;
import com.biblioteca.api.dto.BookDTO;
import com.biblioteca.exceptions.BusinessException;
import com.biblioteca.service.BibliotecaService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BibliotecaControllerTest {



    static String BOOK_API = "/api/books";


    @Autowired
    MockMvc mvc;

    @MockBean
    BibliotecaService bibliotecaService;

    @Test
    @DisplayName("Criar um livro")
    public void createBookTeste() throws Exception {

        BookDTO dto = createBook();
        Book savedBook = Book.builder().id(1l).author("joao").title("procastinaçao").isbn("124").build();
        BDDMockito.given(bibliotecaService.save(Mockito.any(Book.class))).willReturn(savedBook);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json);

        mvc
            .perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(1l))
            .andExpect(jsonPath("title").value(dto.getTitle()))
            .andExpect(jsonPath("author").value(dto.getAuthor()))
            .andExpect(jsonPath("isbn").value(dto.getIsbn()))
            ;
    }

    private BookDTO createBook() {
        return BookDTO.builder().author("joao").title("procastinaçao").isbn("124").build();
    }

    @Test
    @DisplayName("Lançar erro quando nao houver dados suficientes para salvar")
    public void createInvalidBookTest() throws Exception {


        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json);

        mvc.perform(request)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errors", hasSize(3)) );
    }

    @Test
    @DisplayName("Lança erro ao tentar cadastrar um book com isbn repitido")
    public void createBookWithDuplicateIsbn() throws Exception{

        BookDTO dto = createBook();

        String json = new ObjectMapper().writeValueAsString(dto);
        String mensagemErro = "Isbn ja cadastrado";
        BDDMockito.given(bibliotecaService.save(Mockito.any(Book.class))).willThrow(new BusinessException(mensagemErro));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post(BOOK_API)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json);

        mvc.perform(request)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errors", hasSize(1)))
            .andExpect(jsonPath("errors[0]").value(mensagemErro));
    }

    @Test
    @DisplayName("deve obter informaçoes do livro")
    public void getBookDetails() throws Exception {
        Long id = 1l;
        Book book = Book.builder().id(id).title(createBook().getTitle()).author(createBook().getAuthor()).isbn(createBook().getIsbn()).build();
        BDDMockito.given(bibliotecaService.getById(id)).willReturn(Optional.of(book));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .get(BOOK_API.concat("/"+id))
            .accept(MediaType.APPLICATION_JSON);

        mvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1l))
            .andExpect(jsonPath("title").value(createBook().getTitle()))
            .andExpect(jsonPath("author").value(createBook().getAuthor()))
            .andExpect(jsonPath("isbn").value(createBook().getIsbn()))
        ;
    }

    @Test
    @DisplayName("Deve retornar not found quando livro nao existir")
    public void bookNotFound() throws Exception {
        BDDMockito.given(bibliotecaService.getById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .get(BOOK_API.concat("/"+1))
            .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
            .andExpect(status().isNotFound());
    }
}
