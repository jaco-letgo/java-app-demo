package com.letgo.book.application.find;

import com.letgo.shared.application.bus.query.QueryResponse;

final public class FindBookQueryResponse implements QueryResponse {
    private final String title;

    public FindBookQueryResponse(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }
}
