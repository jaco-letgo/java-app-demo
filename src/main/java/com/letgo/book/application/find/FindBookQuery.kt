package com.letgo.book.application.find;

import com.letgo.shared.application.bus.query.Query;

final public class FindBookQuery implements Query {
    private final String id;

    public FindBookQuery(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
