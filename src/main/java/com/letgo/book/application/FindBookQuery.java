package com.letgo.book.application;

public class FindBookQuery {
    private final String id;

    public FindBookQuery(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
