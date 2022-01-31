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
        givenAnExistingBookWith(id.toString(), "OlaKeAse");

        ResponseEntity<String> putResponse = put("/book/" + id + "/title/whatever");
        assertSame(HttpStatus.ACCEPTED, putResponse.getStatusCode());
        assertFalse(putResponse.hasBody());

        weWaitForMessagesToBeProcessed();

        ResponseEntity<String> getResponse = get("/book/" + id);
        assertEquals("whatever", getResponse.getBody());
    }

    @Test
    public void itShouldReturnAcceptedResponseEvenWhenBookIsNotFoundForGivenId() {
        ResponseEntity<String> putResponse = put("/book/" + UUID.randomUUID() + "/title/olakease");
        assertSame(HttpStatus.ACCEPTED, putResponse.getStatusCode());
        assertFalse(putResponse.hasBody());
    }
}
