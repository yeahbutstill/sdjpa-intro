package guru.springframework.sdjpaintro.dao.impl;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorDaoImpl implements AuthorDao {

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
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, preparedStatement, connection);
            } catch (SQLException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        } finally {

            try {
                closeAll(resultSet, preparedStatement, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return null;

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
