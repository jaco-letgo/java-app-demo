package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

final public class ChangeTitleFeatureTest extends TestCase {
    @Test
    public void itShouldChangeABookTitle() {
        post("{'id': '1234', 'title': 'olakease'}");

        ResponseEntity<String> getResponse = get("/book/1234");
        assertEquals("olakease", getResponse.getBody());

        ResponseEntity<String> putResponse = put("/book/1234/title/whatever");
        assertSame(HttpStatus.OK, putResponse.getStatusCode());
        assertFalse(putResponse.hasBody());

        ResponseEntity<String> secondGetResponse = get("/book/1234");
        assertEquals("whatever", secondGetResponse.getBody());
    }

    @Test
    public void itShouldReturnAnErrorWhenBookIsNotFoundForGivenId() {
        ResponseEntity<String> putResponse = put("/book/123/title/olakease");
        assertSame(HttpStatus.NOT_FOUND, putResponse.getStatusCode());
        assertFalse(putResponse.hasBody());
    }
}
