package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.domain.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest {

    AuthorDao authorDao;

    @Autowired
    public AuthorDaoIntegrationTest(AuthorDao authorDao) {
        this.authorDao = authorDao;
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

}
