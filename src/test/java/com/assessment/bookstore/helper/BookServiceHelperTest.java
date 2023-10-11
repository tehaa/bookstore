package com.assessment.bookstore.helper;

import com.assessment.bookstore.entity.Book;
import com.assessment.bookstore.repo.BookRepo;
import com.assessment.bookstore.service.helper.BookServiceHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceHelperTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookServiceHelper classUnderTest;

    private String isbn = "bookIsbnTest";

    @Test
    @DisplayName("given valid isbn when check is Book Present and book exist then return true")
    void givenIsbnWhenCheckIsBookPresentAndGetBookReturnBookThenReturnTrue() {
        Book book = new Book();
        Optional<Book> bookOptional = Optional.of(book);
        when(bookRepo.findById(isbn)).thenReturn(bookOptional);
        boolean result = classUnderTest.isBookPresent(isbn);
        Assertions.assertTrue(result);
        verify(bookRepo, times(1)).findById(isbn);

    }

    @Test
    @DisplayName("given valid isbn when check is Book Present  and Book NotExist then return false")
    void givenIsbnWhenCheckIsBookPresentAndGetBookReturnNullThenReturnFalse() {
        Optional<Book> bookOptional = Optional.empty();
        when(bookRepo.findById(isbn)).thenReturn(bookOptional);
        boolean result = classUnderTest.isBookPresent(isbn);
        Assertions.assertFalse(result);
        verify(bookRepo, times(1)).findById(isbn);

    }

    @Test
    @DisplayName("given valid isbn when check is BookNotPresent  and Book NotExist then return true")
    void givenIsbnWhenCheckIsBookNotPresentAndGetBookReturnNullThenReturnTrue() {
        Optional<Book> bookOptional = Optional.empty();
        when(bookRepo.findById(isbn)).thenReturn(bookOptional);
        boolean result = classUnderTest.isBookNotPresent(isbn);
        Assertions.assertTrue(result);
        verify(bookRepo, times(1)).findById(isbn);

    }

    @Test
    @DisplayName("given valid isbn when check is Book Not Present and book  exist then return false")
    void givenIsbnWhenCheckIsBookNotPresentAndGetBookReturnBookThenReturnFalse() {
        Book book = new Book();
        Optional<Book> bookOptional = Optional.of(book);
        when(bookRepo.findById(isbn)).thenReturn(bookOptional);
        boolean result = classUnderTest.isBookNotPresent(isbn);
        Assertions.assertFalse(result);
        verify(bookRepo, times(1)).findById(isbn);

    }

}
