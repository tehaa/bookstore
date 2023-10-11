package com.assessment.bookstore.service;

import com.assessment.bookstore.controller.BookController;
import com.assessment.bookstore.entity.Book;
import com.assessment.bookstore.exception.BadRequestException;
import com.assessment.bookstore.exception.ResourceNotFoundException;
import com.assessment.bookstore.mapper.BookMapper;
import com.assessment.bookstore.model.BookDTO;
import com.assessment.bookstore.repo.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.assessment.bookstore.helper.LoggingHelperService.logInfo;
import static com.assessment.bookstore.util.Constants.*;

@Service
public class BookServiceImpl implements BookService {
    public static final String CLASS_NAME = BookController.class.getName();

    private final BookRepo bookRepo;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepo bookRepo, BookMapper bookMapper) {
        this.bookRepo = bookRepo;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> getBooksByTitleAndAuthor(String title, String authorName) {
        String methodName = " getBooksByTitleAndAuthor()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        List<Book> books = bookRepo.findByAuthorsNameAndTitle(authorName, title);
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
        return bookMapper.toBookDtos(books);
    }

    @Override
    public List<BookDTO> getBooksByTitleOrAuthor(String title, String authorName) {
        String methodName = " getBooksByTitleOrAuthor()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        List<Book> books = bookRepo.findByAuthorsNameOrTitle(authorName, title);
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
        return bookMapper.toBookDtos(books);
    }

    @Override
    public BookDTO addNewBook(BookDTO bookDTO) {
        String methodName = " addNewBook()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        if (Objects.nonNull(bookDTO.getIsbn()) && isBookNotPresent(bookDTO.getIsbn())) {
            Book book = bookMapper.toBook(bookDTO);
            logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
            return bookMapper.toBookDTO(bookRepo.save(book));
        } else {
            logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
            throw new BadRequestException("can't able to add book with invalid data");
        }
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        String methodName = " addNewBook()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        if (Objects.nonNull(bookDTO.getIsbn()) && isBookPresent(bookDTO.getIsbn())) {
            Book book = bookMapper.toBook(bookDTO);
            logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
            return bookMapper.toBookDTO(bookRepo.save(book));
        } else {
            logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
            throw new BadRequestException("can't able to add book with invalid data");
        }
    }

    @Override
    public void deleteBook(String isbn) {
        String methodName = " deleteBook()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        if (isBookPresent(isbn)) {
            bookRepo.deleteById(isbn);
        } else {
            throw new ResourceNotFoundException(Book.class.getName(), ISBN_FIELD_VALUE, isbn);
        }
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
    }

    private boolean isBookPresent(String isbn) {
        Optional<Book> bookOptional = bookRepo.findById(isbn);
        return bookOptional.isPresent();
    }

    private boolean isBookNotPresent(String isbn) {
        return !isBookPresent(isbn);
    }

}
