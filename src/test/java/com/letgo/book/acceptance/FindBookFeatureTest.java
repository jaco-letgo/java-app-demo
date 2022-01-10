package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final public class FindBookFeatureTest extends TestCase {
    @Test
    public void itShouldFindAnExistingBook() {
        UUID id = UUID.randomUUID();
        post("{'id': " + id + ", 'title': 'olakease'}");

        ResponseEntity<String> getResponse = get("/book/" + id);
        assertSame(HttpStatus.ACCEPTED, getResponse.getStatusCode());
        assertEquals("olakease", getResponse.getBody());
    }

    @Test
    public void itShouldReturnAnErrorWhenBookIsNotFoundForGivenId() {
        ResponseEntity<String> getResponse = get("/book/" + UUID.randomUUID());
        assertSame(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertFalse(getResponse.hasBody());
    }
}
