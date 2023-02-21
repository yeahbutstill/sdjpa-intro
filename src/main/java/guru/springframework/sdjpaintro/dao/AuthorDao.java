package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;

import java.sql.SQLException;

public interface AuthorDao {

    Author getById(Long id) throws SQLException;


}
