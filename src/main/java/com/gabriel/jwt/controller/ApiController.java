package com.gabriel.jwt.controller;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gabriel.jwt.model.CallRecord;
import com.gabriel.jwt.service.JWTService;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ApiController {
    private final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private JWTService jwtService;
    private final StopWatch stopWatch = new StopWatch();

    @PostMapping("/validarJwt")
    public ResponseEntity<Boolean> validarJwt(@RequestBody String jwt) {
        this.stopWatch.start();

        this.logger.info("Recebida chamada para validação de um JWT");
        this.logger.trace("JWT que será validado: " + jwt);

        Boolean isValid = null;
        CallRecord callRecord = new CallRecord(jwt);

        try {
            isValid = this.jwtService.validateJwt(jwt);
        }
        catch (RuntimeException | ParseException | JsonProcessingException e) {
            callRecord.setCause(e.getMessage());
            isValid = false;
            this.logger.error("O JWT recebido é inválido. Causa: " + e.getMessage());
        }
        finally {
            callRecord.setValid(isValid);
            this.jwtService.createJwtValidationRecord(callRecord);
        }

        this.stopWatch.stop();

        this.logger.debug("Tempo da chamada: " + this.stopWatch.getTime() + "ms");
        this.logger.info("Finalizado processamento pra chamada de validação de JWT");

        this.stopWatch.reset();
        return ResponseEntity.ok(isValid);
    }
}
