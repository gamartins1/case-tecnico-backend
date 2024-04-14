## Reposit�rio para desenvolvimento da solu��o do case t�cnico de backend

Este reposit�rio tem como objetivo armazenar o processo de desenvolvimento da solu��o proposta ao desafio dos cases arquitetural e t�cnico com foco em backend. <br>
O desafio consiste em duas partes, sendo:

### Case arquitetural:
Foram desenvolvidos um fluxograma e um desenho t�cnico para exemplifica��o do cen�rio proposto de abertura de uma conta em banco.
- **Fluxograma**: Foi desenvolvido com base em um fluxo onde a abertura de conta � solicitada por um meio digital (aplicativo), por�m durante a valida��o dos documentos por parte do banco s�o encontradas algumas inconsist�ncias, logo se faz necess�rio solicitar a retifica��o ao cliente, que tem a possibiliadde de faz�-la novamente por meio digital ou atrav�s da ida na ag�ncia. Ap�s a retifica��o necess�ria e continua��o da an�lise, � determinado se a conta p�de ser aberta, onde em um cen�rio negativo o cliente � informado pelo banco; e caso o contr�rio � solicitada a sua ida at� uma ag�ncia para realizar procedimentos de seguran�a e ter a conta finalmente aberta e dispon�vel para uso.

O arquivo do fluxograma pode ser visualizado atrav�s da plataforma [Draw.io](https://app.diagrams.net/).

[Arquivo do fluxograma](Fluxograma%20desafio%20arquitetural%20Abertura%20de%20conta.drawio)

- **Desenho t�cnico**: O desenho de arquitetura da solu��o visa mapear as t�cnologias/ferramentas que ser�o utilizadas no ciclo de uso da aplica��o. <br>
Para o cen�rio proposto, a arquitetura foi pensada com base nos servi�os AWS, onde como ponto de partida � exposta uma API respons�vel por captar as requests dos clients, e logo em seguida realizar um balanceamento de carga conforme as chamadas recebidas, a fim de n�o sobrecarregar o servi�o principal. <br>
O servi�o contido em um ECS realiza a valida��o dos dados e documentos recebidos na request, e ap�s sua an�lise faz a persist�ncia em um banco de dados e em seguida dispara uma notifica��o ao cliente usando um t�pico SNS para informar ao cliente se h� a necessidade de uma retifica��o ou informa a aceita��o/recusa de abertura da conta, conforme o fluxograma.

O arquivo do desenho t�cnico pode ser visualizado atrav�s da plataforma [Draw.io](https://app.diagrams.net/).

[Arquivo do desenho t�cnico da solu��o](Desenho%20t�cnico%20da%20solu��o.drawio)

### Case t�cnico:

O case t�cnico consiste no desenvolvimento de uma API que realize a valida��o em um texto na estrutura JWT. <br>
Ao receber o JWT, a API valida as seguintes regras:
- Deve ser um JWT v�lido;
- Deve conter apenas 3 claims(Name, Role e Seed);
- A claim Name n�o pode ter car�cter de n�meros;
- A claim Role deve conter apenas 1 dos tr�s valores (Admin, Member e External);
- A claim Seed deve ser um n�mero primo;
- O tamanho m�ximo da claim Name � de 256 caracteres.

Com todas as condicionais acima satisfeitas, � determinado que o JWT recebido � v�lido, caso contr�rio determina-se o JWT como inv�lido.

#### Aplica��o desenvolvida
Este projeto foi desenvolvido utilizando a linguagem Java com Spring, com gerenciamento das depend�ncias em Maven.
 
Foi criada uma API REST para recebimento e processamento do JWT. <br>
A API fica dispon�vel na seguinte URL: [http://<SUA_URL>/api/v1/validarJwt]()

Ao receber a request, a API aplica as regras determinadas para valida��o do JWT, onde ap�s a valida��o � devolvido como response um valor booleano, sendo **true** para o JWT v�lido e **false** para inv�lido.

A API trata da persist�ncia dos JWTs recebidos, criando um registro em banco com alguns par�metros da request, como o pr�rpio JWT, data/hora do recebimento da chamada, etc. <br>
Foi utilizado um banco PostgreSQL para persist�ncia, sendo manipulado por Hibernate como framework de JPA. <br>
O banco de dados � exclusivo da aplica��o e conteinerzado com Docker.

A valida��o do JWT � feita em tr�s camadas, onde:
- **Valida��o t�cnica estrutural**: Valida que o texto codificado recebido atende a estrutura de um JWT (pontos delimitadores, decodifica��o, header/payload/signature, etc). <br>
Essa valida��o foi delegada ao framework Spring Security, no m�dulo Oauth2, preparado para lidar com JWTs;
- **Valida��o h�brida (t�cnica/n�gocio)**: sendo o JWT v�lido, � validado que o Payload � um JSON tamb�m v�lido, e que esse JSON atende a estrutura conhecida pela aplica��o. <br>
Essa valida��o foi delegada ao framework Jackson, que � utilizado para a serializa��o/desserializa��o de JSONs, logo permite sabermos se as Claims do Payload estruturam o JSON esperado.
- **Valida��o de neg�cio**: Com o JSON v�lido, s�o aplicadas as regras de neg�cio (mencionadas anteriormente). <br>
Ainda com o Jackson, em seu processo de desserializa��o ele popula todos os atributos do objeto que representar� o JSON, e ele faz isso atrav�s dos setters. Sabendo disso, as regras de neg�cio s�o aplicadas pelo pr�prio Jackson ao acessar os setters, onde neles foram aplicadas as valida��es, e em caso de qualquer viola��o ocorre uma exce��o representativa da regra violada.

A API � consultada por uma tela na aplica��o, onde o JWT pode ser validado:
![](__docs__/telaDeValidacaoDoJwt.png)
![](__docs__/jwtValidado.png)
Todas as valida��es de JWT (tanto por chamada externa quanto por tela) ficam registradas e podem ser consultadas em uma tela da aplica��o:
![](__docs__/registrosJwt.png)
As telas foram desenvolvidas utilizando HTML com Thymeleaf, JavaScript.

#### Informa��es de uso da aplica��o
- � necess�rio possuir o Docker instalado, pois a aplica��o executar� o [script Docker](docker-compose.yml) para cria��o de um container para disponibiliza��o do banco de dados;
- A API pode ser testada tamb�m via Postman: [Link dos projetos com a cole��o de requests](__docs__/API/Valida��o%20de%20JWT.json);