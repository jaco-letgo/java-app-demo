package com.letgo.book.application;

public class FindBookQueryResponse {
    private final String title;

    public FindBookQueryResponse(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }
}
