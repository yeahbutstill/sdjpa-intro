package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaSliceTest {

    BookRepository bookRepository;

    @Autowired
    public JpaSliceTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /********
     * The default behavior Spring Boot on a test context is to run this transactional context and roll back...
     * 99 percent of the time you don't want test population data to other tests
     * that typically will cause bad things to happen
     *******/
    @Commit
    @Order(1)
    @Test
    void testSplice() {
        long counterBefore = bookRepository.count();
        AssertionsForClassTypes.assertThat(counterBefore).isEqualTo(2);

        bookRepository.save(new Book("Testing Java Microservices", "9781617292897", "Manning", null));
        long counterAfter = bookRepository.count();
        AssertionsForClassTypes.assertThat(counterBefore).isLessThan(counterAfter);
    }

    @Order(2)
    @Test
    void testSpliceTransaction() {
        long counterBefore = bookRepository.count();
        AssertionsForClassTypes.assertThat(counterBefore).isEqualTo(3);
    }
}
