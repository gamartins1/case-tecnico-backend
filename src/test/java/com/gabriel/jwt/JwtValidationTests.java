package com.gabriel.jwt;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.gabriel.jwt.exception.NameValidationRuleException;
import com.gabriel.jwt.service.JWTService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
class JwtValidationTests {

	private final JWTService jwtService = new JWTService();

	/**
	 * Teste do case 1: <br>
	 * Jwt válido.
	 */
	@Test
	void validacaoCase1_JwtValido() throws IOException, ParseException {
		String jwtContent = new String(Objects.requireNonNull(this.getClass()
			.getClassLoader()
			.getResourceAsStream("cases/case1.txt"))
			.readAllBytes());

		Assert.assertTrue(this.jwtService.validateJwt(jwtContent));

	}

	/**
	 * Teste do case 2: <br>
	 * Jwt com estrutura inválida.
	 */
	@Test
	void validacaoCase2_JwtInvalido() throws IOException {

		String jwt = new String(Objects.requireNonNull(this.getClass()
			.getClassLoader()
			.getResourceAsStream("cases/case2.txt"))
			.readAllBytes());

		Assert.assertThrows(ParseException.class, () -> this.jwtService.validateJwt(jwt));

	}

	/**
	 * Teste do case 3: <br>
	 * Jwt com a Claim 'Name' contendo números.
	 */
	@Test
	void validacaoCase3_JwtInvalido() throws IOException {
		String jwt = new String(Objects.requireNonNull(this.getClass()
			.getClassLoader()
			.getResourceAsStream("cases/case3.txt"))
			.readAllBytes());

		Exception exception = Assert.assertThrows(JsonMappingException.class, () -> this.jwtService.validateJwt(jwt));

		Assert.assertTrue(exception.getCause() instanceof NameValidationRuleException);
	}

	/**
	 * Teste do case 4: <br>
	 * Jwt contendo a Claim 'Org', que não está mapeada na estrutura esperada.
	 */
	@Test
	void validacaoCase4_JwtInvalido() throws IOException {
		String jwt = new String(Objects.requireNonNull(this.getClass()
			.getClassLoader()
			.getResourceAsStream("cases/case4.txt"))
			.readAllBytes());

		Assert.assertThrows(UnrecognizedPropertyException.class, () -> this.jwtService.validateJwt(jwt));
	}
}
