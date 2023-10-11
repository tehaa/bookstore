package com.assessment.bookstore.service;

import com.assessment.bookstore.entity.Book;
import com.assessment.bookstore.model.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getBooksByTitleAndAuthor(String title, String authorName);

    List<BookDTO> getBooksByTitleOrAuthor(String title, String authorName);

    BookDTO addNewBook(BookDTO bookDTO);

    BookDTO updateBook(BookDTO bookDTO);

    void deleteBook(String isbn);

}
