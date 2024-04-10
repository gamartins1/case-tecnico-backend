package com.gabriel.jwt.utils;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import com.gabriel.jwt.exception.RoleValidationRuleException;

public enum Role {
    ADMIN("Admin"),
    EXTERNAL("External"),
    MEMBER("Member");

    private final String value;
    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static void validateRole(String value) throws RoleValidationRuleException {
        stream(values())
            .filter(roleValue -> roleValue.getValue().equals(value))
            .findFirst()
            .orElseThrow(() -> new RoleValidationRuleException("A role recebida é inválida. Era esperado algum dos seguintes valores: " + getRoleValues()));
    }

    private static String getRoleValues() {
        return stream(values()).map(Object::toString).collect(joining(", "));
    }
}
