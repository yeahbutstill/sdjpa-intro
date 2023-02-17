package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@DataJpaTest // By default is going to use an H2 in memory database
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // and it's going to try to use dont overide MYSQL configuration
class MySQLIntegrationTest {

    BookRepository bookRepository;

    @Autowired
    public MySQLIntegrationTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void testMySQL() {
        long counterBefore = bookRepository.count();
        AssertionsForClassTypes.assertThat(counterBefore).isEqualTo(2);
    }

}
