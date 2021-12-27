package com.letgo.book.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class TestCase {
    public static final String DOMAIN_NAME = "http://localhost:";
    @LocalServerPort
    protected int port;
    @Autowired
    protected TestRestTemplate restTemplate;

    protected String get(String endpoint) {
        return restTemplate.getForObject(DOMAIN_NAME + port + endpoint, String.class);
    }
}
