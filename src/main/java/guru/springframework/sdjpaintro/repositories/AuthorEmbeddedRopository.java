package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.composite.AuthorEmbedded;
import guru.springframework.sdjpaintro.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRopository extends JpaRepository<AuthorEmbedded, NameId> {
}
