package guru.springframework.sdjpaintro.dao.impl;

import guru.springframework.sdjpaintro.dao.AuthorDao;
import guru.springframework.sdjpaintro.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public AuthorDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {

        TypedQuery<Author> query = getEntityManager().createQuery("SELECT a FROM Author a WHERE " +
                "a.firstName = :first_name AND a.lastName = :last_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        return query.getSingleResult();

    }

    @Override
    public Author saveNewAuthor(Author author) {

        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();

        return author;

    }

    @Override
    public Author updateAuthor(Author author) {

        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.clear();

        return entityManager.find(Author.class, author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {

        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Author author = entityManager.find(Author.class, id);
        entityManager.remove(author);
        entityManager.flush();
        entityManager.getTransaction().commit();

    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}