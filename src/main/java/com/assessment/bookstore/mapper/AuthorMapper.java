package com.assessment.bookstore.mapper;

import com.assessment.bookstore.entity.Author;
import com.assessment.bookstore.model.AuthorDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO toAuthorDto(Author author);

    List<AuthorDTO> toAuthorDtos(List<Author> authors);

}
