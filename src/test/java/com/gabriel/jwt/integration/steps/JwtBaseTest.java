package com.gabriel.jwt.integration.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public abstract class JwtBaseTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity <Boolean> response;
    private String jwt;

    public void getJwt(String caseFilename) throws IOException {
        jwt = new String(Objects.requireNonNull(this.getClass()
            .getClassLoader()
            .getResourceAsStream("cases/" + caseFilename))
            .readAllBytes());
    }

    public void doPostRequest() {
        HttpEntity <String> httpEntity = new HttpEntity <>(jwt);
        response  = this.restTemplate.exchange("/api/v1/validarJwt", POST, httpEntity, Boolean.class);
    }

    public void validateStatusCode(int statusCode) {
        assertEquals(HttpStatusCode.valueOf(statusCode), response.getStatusCode());
    }

    public void validateResponse(String isValid) {
        assertEquals(Boolean.valueOf(isValid), response.getBody());
    }
}