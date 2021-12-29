package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;

final public class CreateBookFeatureTest extends TestCase {
    @Test
    public void itShouldCreateABook() {
        post("/book", "{'id': '1234', 'title': 'olakease'}");

        assert get("/book/1234").equals("olakease");
    }
}
