package com.gabriel.jwt;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.gabriel.jwt.exception.NameValidationRuleException;
import com.gabriel.jwt.exception.RoleValidationRuleException;
import com.gabriel.jwt.exception.SeedValidationRuleException;
import com.gabriel.jwt.service.JWTService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
class JwtClaimsValidationTests {

    private final JWTService jwtService = new JWTService();

    /**
     * Teste do cenário em que a Claim 'Seed' possui como valor um número primo
     */
    @Test
    void validacaoClaimSeed_numeroPrimo() throws IOException {
        String jwtContent = new String(Objects.requireNonNull(this.getClass()
            .getClassLoader()
            .getResourceAsStream("invalidClaims/claimWithSeedPrime.txt"))
            .readAllBytes());

        Exception exception = Assert.assertThrows(JsonMappingException.class, () -> this.jwtService.validateJwt(jwtContent));

        Assert.assertTrue(exception.getCause() instanceof SeedValidationRuleException);
    }

    /**
     * Teste do cenário em que a Claim 'Role' possui como valor uma role desconhecida (não mapeada no enum de roles)
     */
    @Test
    void validacaoClaimRole_valorInvalido() throws IOException {
        String jwtContent = new String(Objects.requireNonNull(this.getClass()
            .getClassLoader()
            .getResourceAsStream("invalidClaims/claimWithRoleInvalidValue.txt"))
            .readAllBytes());

        Exception exception = Assert.assertThrows(JsonMappingException.class, () -> this.jwtService.validateJwt(jwtContent));

        Assert.assertTrue(exception.getCause() instanceof RoleValidationRuleException);
    }

    /**
     * Teste do cenário em que a Claim 'Name' possui o valor com mais de 256 caracteres
     */
    @Test
    void validacaoClaimName_tamanhoMaximoExcedente() throws IOException {
        String jwtContent = new String(Objects.requireNonNull(this.getClass()
            .getClassLoader()
            .getResourceAsStream("invalidClaims/claimWithNameLength.txt"))
            .readAllBytes());

        Exception exception = Assert.assertThrows(JsonMappingException.class, () -> this.jwtService.validateJwt(jwtContent));

        Assert.assertTrue(exception.getCause() instanceof NameValidationRuleException);
    }
}
