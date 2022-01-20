package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final public class CreateBookFeatureTest extends TestCase {
    @Test
    public void itShouldCreateABook() {
        UUID id = UUID.randomUUID();
        ResponseEntity<String> postResponse = post("{'id': " + id + ", 'title': 'OlaKeAse'}");
        assertSame(HttpStatus.CREATED, postResponse.getStatusCode());
        assertFalse(postResponse.hasBody());

        weWaitForEventsToBeProcessed();

        ResponseEntity<String> getResponse = get("/book/" + id);
        assertEquals("OLAKEASE", getResponse.getBody());
    }
}
