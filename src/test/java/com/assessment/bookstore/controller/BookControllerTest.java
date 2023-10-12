package com.assessment.bookstore.controller;

import com.assessment.bookstore.BookStoreApplication;
import com.assessment.bookstore.exception.ResourceNotFoundException;
import com.assessment.bookstore.exception.RestResponseEntityExceptionHandler;
import com.assessment.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.assessment.bookstore.constant.ApiEndpoints.BOOK_CONTROLLER_BASE_URL;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("book-store-api")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BookStoreApplication.class)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private BookService bookService;

    private String isbn = "bookIsbnTest";

    private MockMvc mockMvc;

    @Autowired
    private BookController bookController;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("given valid isbn when call api delete book and delete book service didn't throw exception then status should be no content")
    @WithMockUser(authorities = "ROLE_DELETE")
    void givenValidIsbnWhenDeleteBookAndBookDeletedThenStatusISNoContent() throws Exception {
        doNothing().when(bookService).deleteBook(isbn);
        mockMvc.perform(delete(BOOK_CONTROLLER_BASE_URL + "/" + isbn))
                .andExpect(status().isNoContent());
        verify(bookService, times(1)).deleteBook(isbn);

    }

    @Test
    @DisplayName("given valid isbn when call api delete book and delete book service  throw record not found exception then status should be no 404")
    @WithMockUser(authorities = "ROLE_DELETE")
    void givenValidIsbnWhenDeleteBookThrowExceptionThenStatusIs404() throws Exception {
        doThrow(new ResourceNotFoundException("record not found")).when(bookService).deleteBook(isbn);
        mockMvc.perform(delete(BOOK_CONTROLLER_BASE_URL + "/" + isbn))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("given valid isbn when call api delete with invalid role then status is forbidden")
    @WithMockUser(authorities = "ivalid_role")
    void givenValidIsbnWhenWithInvalidRoleThenStatusIsForbidden() throws Exception {
        mockMvc.perform(delete(BOOK_CONTROLLER_BASE_URL + "/" + isbn))
                .andExpect(status().isForbidden());

    }

}
