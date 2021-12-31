package com.letgo.book.application.changeTitle;

public class ChangeTitleCommand {
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
