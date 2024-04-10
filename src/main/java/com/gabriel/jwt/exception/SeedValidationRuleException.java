package com.gabriel.jwt.exception;

public class SeedValidationRuleException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public SeedValidationRuleException(String message) {
        super(message);
    }
}
