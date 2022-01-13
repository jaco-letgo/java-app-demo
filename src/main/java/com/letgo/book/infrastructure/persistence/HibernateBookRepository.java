package com.letgo.book.infrastructure.persistence;

import com.letgo.book.domain.Book;
import com.letgo.book.domain.BookId;
import com.letgo.book.domain.BookRepository;
import com.letgo.book.infrastructure.persistence.mapping.HibernateBookEntity;
import com.letgo.book.infrastructure.persistence.mapping.HibernateBookMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
final public class HibernateBookRepository implements BookRepository {
    private final SessionFactory sessionFactory;

    public HibernateBookRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Book> find(BookId id) {
        Transaction transaction = session().beginTransaction();
        Optional<HibernateBookEntity> hibernateEntity = Optional.ofNullable(findById(id));
        transaction.commit();
        if (hibernateEntity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(HibernateBookMapper.toDomainEntity(hibernateEntity.get()));
    }

    @Override
    public void save(Book book) {
        Transaction transaction = session().beginTransaction();
        session().saveOrUpdate(HibernateBookMapper.toOrmEntity(book));
        transaction.commit();
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    private HibernateBookEntity findById(BookId id) {
        return session().byId(HibernateBookEntity.class).load(id);
    }
}
