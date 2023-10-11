package com.assessment.bookstore.mapper;

import com.assessment.bookstore.entity.Book;
import com.assessment.bookstore.model.BookDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toBookDTO(Book book);

    List<BookDTO> toBookDtos(List<Book> books);

    Book toBook(BookDTO bookDTO);
}
