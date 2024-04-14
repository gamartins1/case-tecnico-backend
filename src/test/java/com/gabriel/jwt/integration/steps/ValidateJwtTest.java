package com.gabriel.jwt.integration.steps;

import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValidateJwtTest extends JwtBaseTest {

    @Given("Desejado validar o JWT do case {string}")
    public void desejadoValidarOJwtDoCase(String jwt) throws IOException {
        super.getJwt(jwt);
    }

    @When("Minha API receber uma request")
    public void minhaApiReceberUmaRequest() {
        super.doPostRequest();
    }

    @Then("O Status Code da response deve ser {int}")
    public void oStatusCodeDaResponseDeveSer(int statusCode) {
        super.validateStatusCode(statusCode);
    }

    @And("A response deve ser {string}")
    public void aResponseDeveSer(String isValid) {
        super.validateResponse(isValid);
    }
}
