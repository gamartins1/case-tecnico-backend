package com.gabriel.jwt.service;


import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.jwt.model.MyJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    public Boolean validateJwt(String jwtContent) throws RuntimeException, ParseException, JsonProcessingException {
        JWT jwt = JWTParser.parse(jwtContent);
        new ObjectMapper().readValue(jwt.getJWTClaimsSet().toString(), MyJWT.class);

        return true;
    }
}
