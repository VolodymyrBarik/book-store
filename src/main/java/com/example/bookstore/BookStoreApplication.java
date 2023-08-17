package com.example.bookstore;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {
    private final BookService bookService;

    public BookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Kobzar");
            book.setAuthor("Taras Shevchenko");
            book.setDescription("\"Kobzar\" is a collection of poetic works of the Ukrainian poet"
                    + ", artist and public figure Taras Shevchenko. "
                    + "Issued in 1840, \"Kobzar\" became a symbol of national identity "
                    + "and the movement for independence of Ukraine.");
            book.setPrice(BigDecimal.valueOf(1000));
            book.setIsbn("0267382073");
            book.setCoverImage("d:\\kobzar_cover_image.jpg");
            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }

}
