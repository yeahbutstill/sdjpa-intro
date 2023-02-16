package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "Random House");
        logger.info("Id before save: {}", bookDDD.getId());
        Book savedBookDDD = bookRepository.save(bookDDD);
        logger.info("Id after save: {}", savedBookDDD.getId());

        Book bookSIA = new Book("Spring in Action", "321", "Main");
        logger.info("Id before save: {}", bookSIA.getId());
        Book savedBookSIA = bookRepository.save(bookSIA);
        logger.info("Id after save : {}", savedBookSIA.getId());

        bookRepository.findAll()
                .forEach(book -> {
                    logger.info("Id: {}", book.getId());
                    logger.info("Title: {}", book.getTitle());
                    logger.info("ISBN: {}", book.getIsbn());
                    logger.info("Publisher: {}", book.getPublisher());
                });

    }

}
