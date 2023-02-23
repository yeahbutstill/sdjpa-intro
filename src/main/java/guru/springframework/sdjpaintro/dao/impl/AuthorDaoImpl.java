package guru.springframework.sdjpaintro.dao.impl;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.domain.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final Logger logger = LoggerFactory.getLogger(AuthorDaoImpl.class);
    private static final String CONTEXT_AUTHOR = "context author";

    private final DataSource dataSource;

    @Autowired
    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Author getById(Long id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT  * FROM author WHERE id = ?");
            preparedStatement.setLong(1, id);

            // This not best practice
            //resultSet = statement.executeQuery("SELECT * FROM author WHERE id = " + id);

            // Do this
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getAuthorFromResultSet(resultSet);

            }
        } catch (SQLException e) {
            logger.info(CONTEXT_AUTHOR, e);
        } finally {
            try {
                closeAll(resultSet, preparedStatement, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_AUTHOR, e);
            }
        }

        return null;

    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE first_name = ? AND last_name = ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getAuthorFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            logger.info(CONTEXT_AUTHOR, e);
        } finally {

            try {
                closeAll(resultSet, preparedStatement, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_AUTHOR, e);
            }

        }

        return null;

    }

    @Override
    public Author saveNewAuthor(Author author) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO author (first_name, last_name) VALUES (?, ?) RETURNING id");
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.execute();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT LASTVAL()");

            if (resultSet.next()) {
                Long savedId = resultSet.getLong(1);
                return this.getById(savedId);
            }

        } catch (SQLException e) {
            logger.info(CONTEXT_AUTHOR, e);
        } finally {
            try {
                assert statement != null;
                statement.close();
            } catch (SQLException e) {
                logger.info(CONTEXT_AUTHOR, e);
            }
            try {
                closeAll(resultSet, preparedStatement, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_AUTHOR, e);
            }

        }

        return null;

    }

    @Override
    public Author updateAuthor(Author author) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE author SET first_name = ?, last_name = ? WHERE author.id = ?");
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, author.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            logger.info(CONTEXT_AUTHOR, e);
        } finally {

            try {
                closeAll(null, preparedStatement, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_AUTHOR, e);
            }

        }

        return this.getById(author.getId());

    }

    @Override
    public void deleteAuthorById(Long id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM author WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.info(CONTEXT_AUTHOR, e);
        } finally {
            try {
                closeAll(null, preparedStatement, connection);
            } catch (SQLException e) {
                logger.info(CONTEXT_AUTHOR, e);
            }
        }

    }

    private void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) throws SQLException {

        if (resultSet != null) {
            resultSet.close();
        }

        if (preparedStatement != null) {
            preparedStatement.close();
        }

        if (connection != null) {
            connection.close();
        }

    }

    private Author getAuthorFromResultSet(ResultSet resultSet) throws SQLException {

        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLastName(resultSet.getString("last_name"));

        return author;

    }

}