package com.gabriel.jwt.integration.steps;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.gabriel.jwt.JwtApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = JwtApplication.class, webEnvironment = RANDOM_PORT)
public class CucumberConfiguration {


}
