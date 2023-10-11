package com.assessment.bookstore.fixture;

import com.assessment.bookstore.entity.Book;
import com.assessment.bookstore.model.BookDTO;

public class BookFixture {

    public static BookDTO getBookDto(String isbn){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(isbn);
        return bookDTO;

    }

    public static Book getBook(String isbn){
        Book book = new Book();
        book.setIsbn(isbn);
        return book;

    }
}
