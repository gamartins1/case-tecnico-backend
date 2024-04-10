package com.gabriel.jwt.exception;

public class RoleValidationRuleException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public RoleValidationRuleException(String message) {
        super(message);
    }
}
