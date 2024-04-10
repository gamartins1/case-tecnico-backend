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
     * Aplica a regra de valida��o na claim 'Role', onde o valor recebido deve ser algum dos valores mapeados no enum de roles
     */
    private void validateRoleValue(String role) {
        Role.validateRole(role);
    }

    /**
     * Aplica uma regra interna de valida��o no valor recebido para a claim 'Seed', garantindo que o valor recebido � num�rico
     */
    private void validateSeedAsInteger(String seed) {
        if(!isDigits(seed)) {
            throw new SeedValidationRuleException("O valor informado para Claim 'Seed' n�o respeita a regra de ser um valor num�rico");
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
     * Aplica a regra de valida��o na claim 'Seed', onde o valor esperado deve ser um n�mero primo.
     */
    private void validateSeedAsPrime(String seed) {
        this.validateSeedAsInteger(seed);
        int seedValue = Integer.parseInt(seed);

        if(!this.isPrime(seedValue)) {
            throw new SeedValidationRuleException("O valor informado para Claim 'Seed' n�o respeita a regra de n�o ter como valor um n�mero primo");
        }
    }

    /**
     * Aplica a regra de valida��o na Claim 'Name', onde ela n�o pode possuir mais de 256 caracteres
     */
    private void validateNameLength(String name) {
        if(name.length() > 256) {
            throw new NameValidationRuleException("A Claim 'Name' n�o pode possuir mais do que 256 caracteres. Tamanho do valor recebido: " + name.length());
        }
    }

    /**
     * Aplica a regra de valida��o na Claim 'Name', onde ela n�o pode possuir um valor que contenha caracteres num�ricos
     */
    private void validateNameValue(String name) {
        if(Pattern.compile(".*\\d.*").matcher(name).matches()) {
            throw new NameValidationRuleException("A Claim 'Name' possui o valor inv�lido. N�o � permitido um nome com valores num�ricos");
        }
    }
}
