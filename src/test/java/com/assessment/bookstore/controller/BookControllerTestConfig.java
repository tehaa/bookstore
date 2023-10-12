package com.assessment.bookstore.controller;

import com.assessment.bookstore.service.BookServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("book-store-api")
@Configuration
public class BookControllerTestConfig {

    @Bean
    @Primary
    public BookServiceImpl bookServiceIm() {
        return Mockito.mock(BookServiceImpl.class);
    }

}
