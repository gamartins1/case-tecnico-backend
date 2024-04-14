Feature: Testes de integração da operação de validação de JWT

  Scenario: JWT valido
    Given Desejado validar o JWT do case "case1.txt"
    When Minha API receber uma request
    Then O Status Code da response deve ser 200
    And A response deve ser "true"

  Scenario: JWT invalido
    Given Desejado validar o JWT do case "case2.txt"
    When Minha API receber uma request
    Then O Status Code da response deve ser 200
    And A response deve ser "false"

  Scenario: JWT invalido
    Given Desejado validar o JWT do case "case3.txt"
    When Minha API receber uma request
    Then O Status Code da response deve ser 200
    And A response deve ser "false"

  Scenario: JWT invalido
    Given Desejado validar o JWT do case "case4.txt"
    When Minha API receber uma request
    Then O Status Code da response deve ser 200
    And A response deve ser "false"