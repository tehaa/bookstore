package com.assessment.bookstore.service.helper;

import com.assessment.bookstore.entity.Book;
import com.assessment.bookstore.repo.BookRepo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookServiceHelper {

    private final BookRepo bookRepo;

    public BookServiceHelper(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public boolean isBookPresent(String isbn) {
        Optional<Book> bookOptional = bookRepo.findById(isbn);
        return bookOptional.isPresent();
    }

    public boolean isBookNotPresent(String isbn) {
        return !isBookPresent(isbn);
    }
}
