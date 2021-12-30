package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

final public class CreateBookFeatureTest extends TestCase {
    @Test
    public void itShouldCreateABook() {
        ResponseEntity<String> postResponse = post("{'id': '1234', 'title': 'olakease'}");
        assertSame(postResponse.getStatusCode(), HttpStatus.CREATED);
        assertFalse(postResponse.hasBody());

        ResponseEntity<String> getResponse = get("/book/1234");
        assertEquals("olakease", getResponse.getBody());
    }
}
