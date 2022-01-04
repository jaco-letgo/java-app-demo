package com.letgo.book.application.changeTitle;

import com.letgo.shared.application.bus.command.Command;

public class ChangeTitleCommand implements Command {
    private final String id;
    private final String newTitle;

    public ChangeTitleCommand(String id, String newTitle) {
        this.id = id;
        this.newTitle = newTitle;
    }

    public String id() {
        return id;
    }

    public String newTitle() {
        return newTitle;
    }
}
