package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;

final public class GetBookFeatureTest extends TestCase {
    @Test
    public void itShouldGetABookById() {
        assert get("/book/123").contains("com.letgo.book.domain.Book@");
    }
}
