package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.BookNatural;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.AuthorEmbedded;
import guru.springframework.sdjpaintro.domain.composite.NameId;
import guru.springframework.sdjpaintro.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySQLIntegrationTest {

    BookRepository bookRepository;
    AuthorUuidRepository authorUuidRepository;
    BookUuidRepository bookUuidRepository;
    BookNaturalRepository bookNaturalRepository;
    AuthorCompositeRepository authorCompositeRepository;
    AuthorEmbeddedRopository authorEmbeddedRopository;

    @Autowired
    public MySQLIntegrationTest(BookRepository bookRepository,
                                AuthorUuidRepository authorUuidRepository,
                                BookUuidRepository bookUuidRepository,
                                BookNaturalRepository bookNaturalRepository,
                                AuthorCompositeRepository authorCompositeRepository,
                                AuthorEmbeddedRopository authorEmbeddedRopository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
        this.bookNaturalRepository = bookNaturalRepository;
        this.authorCompositeRepository = authorCompositeRepository;
        this.authorEmbeddedRopository = authorEmbeddedRopository;
    }

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

    }

    @Test
    void testBookUuid() {
        BookUuid bookUuid = bookUuidRepository.save(new BookUuid());
        assertThat(bookUuid).isNotNull();
        assertThat(bookUuid.getId()).isNotNull();

        BookUuid fetched = bookUuidRepository.getReferenceById(bookUuid.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testAuthorUuid() {
        AuthorUuid authorUuid = authorUuidRepository.save(new AuthorUuid());
        assertThat(authorUuid).isNotNull();
        assertThat(authorUuid.getId()).isNotNull();

        AuthorUuid fetched = authorUuidRepository.getReferenceById(authorUuid.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testBookNaturalId() {
        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("My book");
        BookNatural saved = bookNaturalRepository.save(bookNatural);

        BookNatural fetched = bookNaturalRepository.getReferenceById(saved.getTitle());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testAuthorComposite() {
        NameId nameId = new NameId("Dani", "Setiawan");
        AuthorComposite authorComposite = new AuthorComposite();
        authorComposite.setFirstName(nameId.getFirstName());
        authorComposite.setLastName(nameId.getLastName());
        authorComposite.setCountry("IDN");

        AuthorComposite saved = authorCompositeRepository.save(authorComposite);
        assertThat(saved).isNotNull();

        AuthorComposite fetched = authorCompositeRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }

    @Test
    void testAuthorEmbedded() {
        NameId nameId = new NameId("Maya", "Yasmin");
        AuthorEmbedded authorEmbedded = new AuthorEmbedded(nameId);

        AuthorEmbedded saved = authorEmbeddedRopository.save(authorEmbedded);
        assertThat(saved).isNotNull();

        AuthorEmbedded fetched = authorEmbeddedRopository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }

}


