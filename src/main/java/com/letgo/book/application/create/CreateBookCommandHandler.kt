package com.letgo.book.application.create

import com.letgo.book.domain.Book
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.application.bus.event.DomainEventPublisher

class CreateBookCommandHandler(
    private val repository: BookRepository,
    private val publisher: DomainEventPublisher
) : CommandHandler<CreateBookCommand> {
    override fun handle(command: CreateBookCommand) {
        repository.find(BookId.create(command.id)) ?: Book.create(command.id, command.title, command.occurredOn).also {
            repository.save(it)
            publisher.publish(it.retrieveEvents())
        }
    }
}
