package com.letgo.book.application.create;

import com.letgo.shared.application.bus.command.Command;

final public class CreateBookCommand implements Command {
    private final String id;
    private final String title;

    public CreateBookCommand(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }
}
