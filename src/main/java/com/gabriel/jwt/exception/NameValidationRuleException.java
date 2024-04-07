package com.gabriel.jwt.exception;

public class NameValidationRuleException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public NameValidationRuleException(String message) {
        super(message);
    }
}
