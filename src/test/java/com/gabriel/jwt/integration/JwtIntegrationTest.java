package com.gabriel.jwt.integration;

import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.Objects;

import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@TestInstance(PER_CLASS)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
public class JwtIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testApiJwt() throws IOException {
        String jwt = new String(Objects.requireNonNull(this.getClass()
            .getClassLoader()
            .getResourceAsStream("cases/case1.txt"))
            .readAllBytes());

        HttpEntity<String> httpEntity = new HttpEntity <>(jwt);

        ResponseEntity <Boolean> response = this.testRestTemplate
            .exchange("/api/v1/validarJwt", POST, httpEntity, Boolean.class);

        assertEquals(response.getStatusCode(), OK);
        assertEquals(TRUE, response.getBody());
    }
}
