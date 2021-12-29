package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;

final public class FindBookFeatureTest extends TestCase {
    @Test
    public void itShouldFindAnExistingBook() {
        post("/book", "{'id': '12345', 'title': 'olakease'}");

        assert get("/book/12345").equals("olakease");
    }

    @Test
    public void itShouldReturnAnErrorWhenBookIsNotFoundForGivenId() {
        assert get("/book/123").equals("Book not found with id 123");
    }
}
