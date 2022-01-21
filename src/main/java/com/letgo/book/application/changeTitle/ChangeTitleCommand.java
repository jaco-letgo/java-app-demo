package com.letgo.book.application.changeTitle;

import com.letgo.shared.application.bus.command.Command;

import java.time.LocalDateTime;

final public class ChangeTitleCommand implements Command {
    private final String id;
    private final String newTitle;
    private final LocalDateTime occurredOn;

    public ChangeTitleCommand(String id, String newTitle, LocalDateTime occurredOn) {
        this.id = id;
        this.newTitle = newTitle;
        this.occurredOn = occurredOn;
    }

    public ChangeTitleCommand(String id, String newTitle) {
        this.id = id;
        this.newTitle = newTitle;
        this.occurredOn = LocalDateTime.now();
    }

    public String id() {
        return id;
    }

    public String newTitle() {
        return newTitle;
    }

    public LocalDateTime occurredOn() {
        return occurredOn;
    }
}
