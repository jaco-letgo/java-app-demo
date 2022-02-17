package com.letgo.book.infrastructure.persistence

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.shared.infrastructure.InfrastructureService
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.transaction.annotation.Transactional

@Transactional
@InfrastructureService
open class HibernateBookRepository(
    private val sessionFactory: SessionFactory
) : BookRepository {
    override fun find(id: BookId): Book? = session().find(Book::class.java, id)

    override fun save(book: Book) {
        session().saveOrUpdate(book)
        session().flush()
        session().clear()
    }

    private fun session(): Session = sessionFactory.currentSession
}
