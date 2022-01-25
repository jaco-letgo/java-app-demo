package com.letgo.book.infrastructure.persistence

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.book.infrastructure.persistence.mapping.HibernateBookEntity
import com.letgo.book.infrastructure.persistence.mapping.HibernateBookMapper
import com.letgo.shared.infrastructure.InfrastructureService
import org.hibernate.Session
import org.hibernate.SessionFactory
import java.util.*

@InfrastructureService
class HibernateBookRepository(
    private val sessionFactory: SessionFactory
) : BookRepository {
    override fun find(id: BookId): Optional<Book> {
        val transaction = session().beginTransaction()
        val hibernateEntity = Optional.ofNullable(findById(id))
        transaction.commit()
        return if (hibernateEntity.isEmpty) {
            Optional.empty()
        } else Optional.of(HibernateBookMapper.toDomainEntity(hibernateEntity.get()))
    }

    override fun save(book: Book) {
        val transaction = session().beginTransaction()
        session().saveOrUpdate(HibernateBookMapper.toOrmEntity(book))
        transaction.commit()
    }

    private fun session(): Session {
        return sessionFactory.currentSession
    }

    private fun findById(id: BookId): HibernateBookEntity? {
        return session().find(HibernateBookEntity::class.java, id)
    }
}
