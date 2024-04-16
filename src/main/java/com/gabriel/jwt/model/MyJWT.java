package com.gabriel.jwt.model;

import static org.apache.commons.lang3.math.NumberUtils.isDigits;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriel.jwt.exception.NameValidationRuleException;
import com.gabriel.jwt.exception.SeedValidationRuleException;
import com.gabriel.jwt.utils.Role;


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
        this.validateRoleValue(role);
        this.role = role;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.validateSeedAsPrime(seed);
        this.seed = seed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.validateNameLength(name);
        this.validateNameValue(name);
        this.name = name;
    }

    /**
     * Aplica a regra de validação na claim 'Role', onde o valor recebido deve ser algum dos valores mapeados no enum de roles
     */
    private void validateRoleValue(String role) {
        Role.validateRole(role);
    }

    /**
     * Aplica uma regra interna de validação no valor recebido para a claim 'Seed', garantindo que o valor recebido é numérico
     */
    private void validateSeedAsInteger(String seed) {
        if(!isDigits(seed)) {
            throw new SeedValidationRuleException("O valor informado para Claim 'Seed' não respeita a regra de ser um valor numérico");
        }
    }

    private boolean isPrime(int number) {
        boolean isPrime = false;
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                isPrime = true;
                break;
            }
        }

        return isPrime;
    }

    /**
     * Aplica a regra de validação na claim 'Seed', onde o valor esperado deve ser um número primo.
     */
    private void validateSeedAsPrime(String seed) {
        this.validateSeedAsInteger(seed);
        int seedValue = Integer.parseInt(seed);

        if(!this.isPrime(seedValue)) {
            throw new SeedValidationRuleException("O valor informado para Claim 'Seed' não respeita a regra de não ter como valor um número primo");
        }
    }

    /**
     * Aplica a regra de validação na Claim 'Name', onde ela não pode possuir mais de 256 caracteres
     */
    private void validateNameLength(String name) {
        if(name.length() > 256) {
            throw new NameValidationRuleException("A Claim 'Name' não pode possuir mais do que 256 caracteres. Tamanho do valor recebido: " + name.length());
        }
    }

    /**
     * Aplica a regra de validação na Claim 'Name', onde ela não pode possuir um valor que contenha caracteres numéricos
     */
    private void validateNameValue(String name) {
        if(Pattern.compile(".*\\d.*").matcher(name).matches()) {
            throw new NameValidationRuleException("A Claim 'Name' possui o valor inválido. Não é permitido um nome com valores numéricos");
        }
    }
}
