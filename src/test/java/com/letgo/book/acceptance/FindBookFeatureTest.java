package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

final public class FindBookFeatureTest extends TestCase {
    @Test
    public void itShouldFindAnExistingBook() {
        post("{'id': '12345', 'title': 'olakease'}");

        ResponseEntity<String> getResponse = get("/book/12345");
        assertSame(getResponse.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals("olakease", getResponse.getBody());
    }

    @Test
    public void itShouldReturnAnErrorWhenBookIsNotFoundForGivenId() {
        ResponseEntity<String> getResponse = get("/book/123");
        assertSame(getResponse.getStatusCode(), HttpStatus.NOT_FOUND);
        assertFalse(getResponse.hasBody());
    }
}
