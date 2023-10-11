package com.assessment.bookstore.controller;

import com.assessment.bookstore.model.BookDTO;
import com.assessment.bookstore.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assessment.bookstore.helper.LoggingHelperService.logInfo;
import static com.assessment.bookstore.util.Constants.LOG_METHOD_ENTRY;
import static com.assessment.bookstore.util.Constants.LOG_METHOD_EXIT;

@RestController
@RequestMapping("/api/books")
public class BookController {

    public static final String CLASS_NAME = BookController.class.getName();

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "Get book by title and author")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getBooksByTitleAndAuthor(@RequestParam String title,
                                                                  @RequestParam(required = false) String authorName) {
        String methodName = " getBooksByTitleAndAuthor()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        List<BookDTO> response = bookService.getBooksByTitleAndAuthor(title, authorName);
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @ApiOperation(value = "adding a new book")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> addNewBook(@RequestBody BookDTO bookDTO) {
        String methodName = " addNewBook()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        BookDTO response = bookService.addNewBook(bookDTO);
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "updating book")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        String methodName = " updateBook()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        BookDTO response = bookService.updateBook(bookDTO);
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "delete book")
    @PreAuthorize("hasRole('ROLE_DELETE')")
    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable(name = "isbn") String isbn) {
        String methodName = " deleteBook()";
        logInfo(CLASS_NAME, methodName, LOG_METHOD_ENTRY);
        bookService.deleteBook(isbn);
        logInfo(CLASS_NAME, methodName, LOG_METHOD_EXIT);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
