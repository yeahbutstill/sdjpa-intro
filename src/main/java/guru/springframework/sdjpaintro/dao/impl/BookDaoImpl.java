package guru.springframework.sdjpaintro.dao.impl;

import guru.springframework.sdjpaintro.dao.BookDao;
import guru.springframework.sdjpaintro.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BookDaoImpl implements BookDao {

    private final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
    private static final String CONTEXT_BOOK = "context book";


    @Override
    public Book getById(Long id) {
        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        return null;
    }

    @Override
    public Book saveNewBook(Book book) {
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {

    }

}