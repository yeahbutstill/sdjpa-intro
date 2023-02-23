package guru.springframework.sdjpaintro.dao.impl;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.dao.BookDao;
import guru.springframework.sdjpaintro.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {

    private final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
    private static final String CONTEXT_BOOK = "context book";

    private final DataSource source;
    private final AuthorDao authorDao;

    public BookDaoImpl(DataSource source, AuthorDao authorDao) {
        this.source = source;
        this.authorDao = authorDao;
    }

    @Override
    public Book getById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where id = ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getBookFromRS(resultSet);
            }
        } catch (SQLException e) {
            logger.info(CONTEXT_BOOK, e);
        } finally {
            try {
                closeAll(resultSet, ps, connection);

            } catch (SQLException e) {
                logger.info(CONTEXT_BOOK, e);
            }
        }

        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where title = ?");
            ps.setString(1, title);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getBookFromRS(resultSet);
            }
        } catch (SQLException e) {
            logger.info(CONTEXT_BOOK, e);
        } finally {
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_BOOK, e);
            }
        }

        return null;
    }

    @Override
    public Book saveNewBook(Book book) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        Statement statement = null;
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("INSERT INTO book (isbn, publisher, title, author_id) VALUES (?, ?, ?, ?) RETURNING id");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getPublisher());
            ps.setString(3, book.getTitle());

            if (book.getAuthor() != null) {
                ps.setLong(4, book.getAuthor().getId());
            } else {
                ps.setNull(4, -5);
            }

            ps.execute();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT LASTVAL()");

            if (resultSet.next()) {
                Long savedId = resultSet.getLong(1);
                return this.getById(savedId);
            }

        } catch (SQLException e) {
            logger.info(CONTEXT_BOOK, e);
        } finally {
            try {
                assert statement != null;
                statement.close();
            } catch (SQLException e) {
                logger.info(CONTEXT_BOOK, e);
            }
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_BOOK, e);
            }
        }

        return null;
    }

    @Override
    public Book updateBook(Book book) {
        Connection connection = null;
        PreparedStatement ps = null;


        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("UPDATE book set isbn = ?, publisher = ?, title = ?, author_id = ? where id = ?");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getPublisher());
            ps.setString(3, book.getTitle());

            if (book.getAuthor() != null) {
                ps.setLong(4, book.getAuthor().getId());
            }

            ps.setLong(5, book.getId());
            ps.execute();

        } catch (SQLException e) {
            logger.info(CONTEXT_BOOK, e);
        } finally {
            try {
                closeAll(null, ps, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_BOOK, e);
            }
        }

        return getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("DELETE from book where id = ?");
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            logger.info(CONTEXT_BOOK, e);
        } finally {
            try {
                closeAll(null, ps, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_BOOK, e);
            }
        }
    }

    private Book getBookFromRS(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(1));
        book.setIsbn(resultSet.getString(2));
        book.setPublisher(resultSet.getString(3));
        book.setTitle(resultSet.getString(4));
        book.setAuthor(authorDao.getById(resultSet.getLong(5)));

        return book;
    }

    private void closeAll(ResultSet resultSet, PreparedStatement ps, Connection connection) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }

        if (ps != null){
            ps.close();
        }

        if (connection != null){
            connection.close();
        }
    }
}