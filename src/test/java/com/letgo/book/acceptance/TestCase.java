package com.letgo.book.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class TestCase {
    public static final String DOMAIN_NAME = "http://localhost:";
    @LocalServerPort
    protected int port;
    @Autowired
    protected TestRestTemplate restTemplate;

    protected ResponseEntity<String> get(String endpoint) {
        return restTemplate.getForEntity(DOMAIN_NAME + port + endpoint, String.class);
    }

    protected ResponseEntity<String> post(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(DOMAIN_NAME + port + "/book", entity, String.class);
    }

    protected ResponseEntity<String> put(String url) {
        return restTemplate.exchange(url, HttpMethod.PUT, HttpEntity.EMPTY, String.class);
    }

    protected void givenAnExistingBookWith(String id, String title) {
        post("{'id': " + id + ", 'title': " + title + "}");
        weWaitForEventsToBeProcessed();
    }

    protected void weWaitForEventsToBeProcessed() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
