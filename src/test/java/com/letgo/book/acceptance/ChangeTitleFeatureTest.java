package com.letgo.book.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final public class ChangeTitleFeatureTest extends TestCase {
    @Test
    public void itShouldChangeABookTitle() {
        UUID id = UUID.randomUUID();
        givenAnExistingBookWith(id.toString(), "OLAKEASE");

        ResponseEntity<String> putResponse = put("/book/" + id + "/title/whatever");
        assertSame(HttpStatus.OK, putResponse.getStatusCode());
        assertFalse(putResponse.hasBody());

        ResponseEntity<String> secondGetResponse = get("/book/" + id);
        assertEquals("whatever", secondGetResponse.getBody());
    }

    @Test
    public void itShouldReturnAnErrorWhenBookIsNotFoundForGivenId() {
        ResponseEntity<String> putResponse = put("/book/" + UUID.randomUUID() + "/title/olakease");
        assertSame(HttpStatus.NOT_FOUND, putResponse.getStatusCode());
        assertFalse(putResponse.hasBody());
    }
}
