package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SdjpaIntroApplicationTests {

    BookRepository bookRepository;

    @Autowired
    public SdjpaIntroApplicationTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void testBookRepository() {
        long count = bookRepository.count();
        AssertionsForClassTypes.assertThat(count).isGreaterThan(1);
    }

    @Test
    void contextLoads() {
    }

}
