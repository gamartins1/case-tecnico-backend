package com.gabriel.jwt.controller;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gabriel.jwt.model.CallRecord;
import com.gabriel.jwt.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ApiController {

    @Autowired
    private JWTService jwtService;

    @PostMapping("/validarJwt")
    public ResponseEntity<Boolean> validarJwt(@RequestBody String jwt) {

        Boolean isValid = null;
        CallRecord callRecord = new CallRecord(jwt);

        try {
            isValid = this.jwtService.validateJwt(jwt);
        }
        catch (RuntimeException | ParseException | JsonProcessingException e) {
            callRecord.setCause(e.getMessage());
            isValid = false;
        }
        finally {
            callRecord.setValid(isValid);
            this.jwtService.createJwtValidationRecord(callRecord);
        }

        return ResponseEntity.ok(isValid);
    }
}
