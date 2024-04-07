package com.gabriel.jwt.model;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriel.jwt.exception.NameValidationRuleException;


public class MyJWT {

    @JsonProperty(value = "Role")
    String role;

    @JsonProperty(value = "Seed")
    String seed;


    @JsonProperty(value = "Name")
    String name;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.validateName(name);
        this.name = name;
    }

    /**
     * Aplica a regra de validação na Claim 'Name', onde ela não pode possuir um valor que contenha caracteres numéricos
     */
    private void validateName(String name) {
        if(Pattern.compile(".*\\d.*").matcher(name).matches()) {
            throw new NameValidationRuleException("A Claim 'Name' possuir o valor inválido. Não é permitido um nome com valores numéricos");
        }
    }
}
