package com.assessment.bookstore.repo;

import com.assessment.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, String> {
    List<Book> findByAuthorsNameOrTitle(String authorName, String title);

    List<Book> findByAuthorsNameAndTitle(String authorName, String title);

}
