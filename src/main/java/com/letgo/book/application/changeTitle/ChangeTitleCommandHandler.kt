package com.letgo.book.application.changeTitle

import com.letgo.book.domain.BookFinder
import com.letgo.book.domain.BookId
import com.letgo.book.domain.BookRepository
import com.letgo.book.domain.BookTitle
import com.letgo.shared.application.bus.command.CommandHandler
import com.letgo.shared.application.bus.event.DomainEventPublisher

class ChangeTitleCommandHandler(
    private val repository: BookRepository,
    private val finder: BookFinder,
    private val publisher: DomainEventPublisher,
) : CommandHandler<ChangeTitleCommand> {
    override fun handle(command: ChangeTitleCommand) {
        val book = finder.find(BookId(command.id))
        val newTitle = BookTitle(command.newTitle, command.occurredOn)
        if (book.canChangeTitle(newTitle)) {
            book.changeTitle(newTitle)
            repository.save(book)
            publisher.publish(book.retrieveEvents())
        }
    }
}
