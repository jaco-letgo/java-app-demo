package com.letgo.book.application.create

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.application.event.DomainEventPublisher

class CreateBookCommandHandler(
    private val repository: BookRepository,
    private val publisher: DomainEventPublisher
) : CommandHandler<CreateBookCommand> {
    override fun handle(command: CreateBookCommand) {
        val id = BookId.create(command.id)
        if (repository.find(id).isEmpty) {
            val book = Book.create(id.value(), command.title, command.titleCreatedAt)
            repository.save(book)
            publisher.publish(book.retrieveEvents())
        }
    }
}
