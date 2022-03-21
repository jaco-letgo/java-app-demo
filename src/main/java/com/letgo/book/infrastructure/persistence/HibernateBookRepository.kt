package com.letgo.book.infrastructure.persistence

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookTitle
import com.letgo.shared.domain.OptimisticConcurrencyLock
import com.letgo.shared.domain.criteria.Criteria
import com.letgo.shared.infrastructure.InfrastructureService
import com.letgo.shared.infrastructure.persistance.predicate.PredicateBuilder
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.transaction.annotation.Transactional
import javax.persistence.OptimisticLockException
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@Transactional
@InfrastructureService
open class HibernateBookRepository(
    private val sessionFactory: SessionFactory,
    private val predicateBuilder: PredicateBuilder<Book>,
) : BookRepository {
    override fun all(): List<Book> = session().createQuery("from Book").list() as List<Book>

    override fun find(id: BookId): Book? = session().find(Book::class.java, id)

    override fun findBy(criteria: Criteria): List<Book> {
        val criteriaBuilder = session().criteriaBuilder
        val criteriaQuery: CriteriaQuery<Book> = criteriaBuilder.createQuery(Book::class.java)
        val root: Root<Book> = criteriaQuery.from(Book::class.java)
        criteriaQuery
            .select(root)
            .where(predicateBuilder.build(criteria, root, criteriaBuilder))
            .orderBy(criteriaBuilder.asc(root.get<BookTitle>("title")))

        return session().createQuery(criteriaQuery).resultList
    }

    override fun save(book: Book) {
        try {
            session().saveOrUpdate(book)
            session().flush()
            session().clear()
        } catch (exception: OptimisticLockException) {
            throw OptimisticConcurrencyLock.causedBy(exception)
        }
    }

    private fun session(): Session = sessionFactory.currentSession
}
