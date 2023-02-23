package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.dao.BookDao;
import guru.springframework.sdjpaintro.dao.impl.AuthorDaoImpl;
import guru.springframework.sdjpaintro.dao.impl.BookDaoImpl;
import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@Import({AuthorDaoImpl.class, BookDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest {

    AuthorDao authorDao;
    BookDao bookDao;

    @Autowired
    public AuthorDaoIntegrationTest(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @Test
    void testGetAuthor() {

        Author author = authorDao.getById(1L);
        Assertions.assertThat(author).isNotNull();

    }

    @Test
    void testAuthorByName() {

        Author author = authorDao.findAuthorByName("Craig", "Walls");
        Assertions.assertThat(author).isNotNull();

    }

   @Test
    void testSaveNewAuthor() {

        Author author = new Author();
        author.setFirstName("Maya");
        author.setLastName("Winda");

        Author saved = authorDao.saveNewAuthor(author);
        Assertions.assertThat(saved).isNotNull();

   }

   @Test
    void testUpdateAuthor() {

        Author author = new Author();
        author.setFirstName("Dani");
        author.setLastName("Setiawan");

        Author saved = authorDao.saveNewAuthor(author);
        saved.setLastName("Yuni");

        Author updated = authorDao.updateAuthor(saved);
        Assertions.assertThat(updated.getLastName()).isEqualTo("Yuni");

   }

   @Test
    void testDeleteAuthor() {

        Author author = new Author();
        author.setFirstName("Dani");
        author.setLastName("Setiawan");

        Author saved = authorDao.saveNewAuthor(author);
        authorDao.deleteAuthorById(saved.getId());

       try {
           assertThrows(EmptyResultDataAccessException.class, () ->
               authorDao.getById(saved.getId())
           );
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

   }

   @Test
    void testDeleteBook() {

        Book book = new Book();
        book.setIsbn("4321");
        book.setPublisher("Self");
        book.setTitle("My Book");

        Book saved = bookDao.saveNewBook(book);
        bookDao.deleteBookById(saved.getId());

       try {
           assertThrows(EmptyResultDataAccessException.class, () ->
                   bookDao.getById(saved.getId())
           );
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

   }

   @Test
    void updateBookTest() {

       Book book = new Book();
       book.setIsbn("4321");
       book.setPublisher("Self");
       book.setTitle("My Book");

       Author author = new Author();
       author.setId(3L);
       book.setAuthorId(author.getId());

       Book saved = bookDao.saveNewBook(book);
       saved.setTitle("New Book");

       bookDao.updateBook(saved);

       Book fetched = bookDao.getById(saved.getId());
       Assertions.assertThat(fetched.getTitle()).isEqualTo("New Book");

   }

   @Test
    void testSaveBook() {

        Book book = new Book();
        book.setIsbn("12345");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);
        book.setAuthorId(author.getId());

        Book saved = bookDao.saveNewBook(book);
        Assertions.assertThat(saved).isNotNull();

   }

   @Test
    void testGetBookByName() {

        Book book = bookDao.findBookByTitle("Clean Code");
        Assertions.assertThat(book).isNotNull();

   }

   @Test
    void testGetBookById() {

        Book book = bookDao.getById(3L);
        Assertions.assertThat(book.getId()).isNotNull();

   }

}