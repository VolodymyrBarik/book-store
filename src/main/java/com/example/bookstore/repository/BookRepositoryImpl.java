package com.example.bookstore.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import com.example.bookstore.model.Book;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;


    @Override
    public Book save(Book book) {
        EntityTransaction entityTransaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityTransaction = entityManager.getTransaction();
            entityManager.persist(book);
            entityTransaction.commit();
            return book;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't insert book into db" + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("FROM Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get books from db", e);
        }
    }
}
