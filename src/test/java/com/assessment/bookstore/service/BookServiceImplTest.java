package com.assessment.bookstore.service;

import com.assessment.bookstore.entity.Book;
import com.assessment.bookstore.exception.BadRequestException;
import com.assessment.bookstore.exception.ResourceNotFoundException;
import com.assessment.bookstore.mapper.BookMapper;
import com.assessment.bookstore.model.BookDTO;
import com.assessment.bookstore.repo.BookRepo;
import com.assessment.bookstore.service.helper.BookServiceHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.assessment.bookstore.fixture.BookFixture.getBook;
import static com.assessment.bookstore.fixture.BookFixture.getBookDto;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRepo bookRepo;

    @Mock
    private BookServiceHelper bookServiceHelper;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl classUnderTest;

    private String isbn = "bookIsbnTest";

    @Test
    @DisplayName("given valid isbn when delete book and check book is exist return true then the book deleted")
    void givenValidIsbnWhenDeleteBookAndCheckBookIsExistReturnTrueThenTheBookDeleted() {
        when(bookServiceHelper.isBookPresent(isbn)).thenReturn(true);
        doNothing().when(bookRepo).deleteById(isbn);
        classUnderTest.deleteBook(isbn);
        verify(bookServiceHelper, times(1)).isBookPresent(isbn);
        verify(bookRepo, times(1)).deleteById(isbn);
    }

    @Test
    @DisplayName("given valid isbn when delete book and check book is exist return false Throw record not found exception")
    void givenValidIsbnWhenDeleteBookAndCheckBookIsExistReturnFalseThenThrowException() {
        when(bookServiceHelper.isBookPresent(isbn)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> classUnderTest.deleteBook(isbn));
        verify(bookServiceHelper, times(1)).isBookPresent(isbn);
        verify(bookRepo, times(0)).deleteById(isbn);
    }

    @Test
    @DisplayName("given valid BookDto when update book and book is exist then the book updated successfully")
    void givenValidBookDtoWhenUpdateBookAndBookIsExistThenTheBookUpdatedSuccessfully() {
        BookDTO bookDTO = getBookDto(isbn);
        Book book = getBook(isbn);

        when(bookServiceHelper.isBookPresent(isbn)).thenReturn(true);
        when(bookMapper.toBook(bookDTO)).thenReturn(book);
        when(bookRepo.save(book)).thenReturn(book);
        when(bookMapper.toBookDTO(book)).thenReturn(bookDTO);

        BookDTO response = classUnderTest.updateBook(bookDTO);

        assertNotNull(response);
        Assertions.assertEquals(book.getIsbn(), response.getIsbn());
        verify(bookServiceHelper, times(1)).isBookPresent(isbn);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    @DisplayName("given valid BookDto when update book and book is Not exist then throw record not found exception")
    void givenValidBookDtoWhenUpdateBookAndBookIsNotExistThenThrowRecordNotFoundException() {
        BookDTO bookDTO = getBookDto(isbn);
        when(bookServiceHelper.isBookPresent(isbn)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> classUnderTest.updateBook(bookDTO));

        verify(bookServiceHelper, times(1)).isBookPresent(isbn);
        verify(bookRepo, times(0)).save(any());
    }

    @Test
    @DisplayName("given valid BookDto when add book and book isbn is exist then throw bad request exception")
    void givenValidBookDtoWhenAddBookAndBookISExistThenThrowException(){
        BookDTO bookDTO = getBookDto(isbn);
        when(bookServiceHelper.isBookNotPresent(isbn)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> classUnderTest.addNewBook(bookDTO));

        verify(bookServiceHelper, times(1)).isBookNotPresent(isbn);
        verify(bookRepo, times(0)).save(any());
    }

    @Test
    @DisplayName("given valid BookDto when add book and book is not exist then the book added successfully")
    void givenValidBookDtoWhenAddBookAndBookIsNotExistThenTheBookUpdatedSuccessfully() {
        BookDTO bookDTO = getBookDto(isbn);
        Book book = getBook(isbn);

        when(bookServiceHelper.isBookNotPresent(isbn)).thenReturn(true);
        when(bookMapper.toBook(bookDTO)).thenReturn(book);
        when(bookRepo.save(book)).thenReturn(book);
        when(bookMapper.toBookDTO(book)).thenReturn(bookDTO);

        BookDTO response = classUnderTest.addNewBook(bookDTO);

        assertNotNull(response);
        Assertions.assertEquals(book.getIsbn(), response.getIsbn());
        verify(bookServiceHelper, times(1)).isBookNotPresent(isbn);
        verify(bookRepo, times(1)).save(book);
    }
}
