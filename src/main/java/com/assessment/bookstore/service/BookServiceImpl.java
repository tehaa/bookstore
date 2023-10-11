package com.assessment.bookstore.service;

import com.assessment.bookstore.controller.BookController;
import com.assessment.bookstore.entity.Book;
import com.assessment.bookstore.exception.BadRequestException;
import com.assessment.bookstore.exception.ResourceNotFoundException;
import com.assessment.bookstore.mapper.BookMapper;
import com.assessment.bookstore.model.BookDTO;
import com.assessment.bookstore.repo.BookRepo;
import com.assessment.bookstore.service.helper.BookServiceHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.assessment.bookstore.helper.LoggingHelperService.logInfo;
import static com.assessment.bookstore.util.Constants.*;

@Service
public class BookServiceImpl implements BookService {
    public static final String CLASS_NAME = BookController.class.getName();

    private final BookRepo bookRepo;

    private final BookMapper bookMapper;

    private final BookServiceHelper bookServiceHelper;

    public BookServiceImpl(BookRepo bookRepo, BookMapper bookMapper, BookServiceHelper bookServiceHelper) {
        this.bookRepo = bookRepo;
        this.bookMapper = bookMapper;
        this.bookServiceHelper = bookServiceHelper;
    }

    @Override
    public List<BookDTO> getBooksByTitleAndAuthor(String title, String authorName) {
        String methodName = " getBooksByTitleAndAuthor()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        List<Book> books;
        if (Objects.nonNull(authorName)) {
            books = bookRepo.findByAuthorsNameAndTitle(authorName, title);
        } else {
            books = bookRepo.findByTitle(title);
        }
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
        return bookMapper.toBookDtos(books);
    }

    @Override
    public BookDTO addNewBook(BookDTO bookDTO) {
        String methodName = " addNewBook()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        if (Objects.nonNull(bookDTO.getIsbn()) && bookServiceHelper.isBookNotPresent(bookDTO.getIsbn())) {
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
        if (Objects.nonNull(bookDTO.getIsbn()) && bookServiceHelper.isBookPresent(bookDTO.getIsbn())) {
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
        if (bookServiceHelper.isBookPresent(isbn)) {
            bookRepo.deleteById(isbn);
        } else {
            throw new ResourceNotFoundException(Book.class.getName(), ISBN_FIELD_VALUE, isbn);
        }
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
    }

}
