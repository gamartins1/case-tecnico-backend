package com.gabriel.jwt.service;


import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.jwt.model.CallRecord;
import com.gabriel.jwt.model.MyJWT;
import com.gabriel.jwt.repository.CallRecordRepository;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    private final Logger logger = LoggerFactory.getLogger(JWTService.class);
    @Autowired
    private CallRecordRepository callRecordRepository;

    public Boolean validateJwt(String jwtContent) throws RuntimeException, ParseException, JsonProcessingException {
        JWT jwt = JWTParser.parse(jwtContent);
        new ObjectMapper().readValue(jwt.getJWTClaimsSet().toString(), MyJWT.class);

        return true;
    }

    public void createJwtValidationRecord(CallRecord callRecord) {
        this.callRecordRepository.save(callRecord);

        this.logger.trace("Criado registro de validação do JWT: " + callRecord.getId());
    }
}
