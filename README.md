# Transfer API

Esta � uma API para gest�o de transfer�ncias financeiras. A API foi desenvolvida em Java utilizando o framework Spring Boot 3 e Java 17. Ela permite gerenciar tr�s tipos de transfer�ncias: `SELLER`, `CONCILIACAO` e `CONTABIL`.

## Funcionalidades

- Criar uma novo repasse
- Atualizar um repasse existente
- Excluir um repasse
- Consultar repasse com filtros (pagina��o e ordena��o suportadas)
- Consultar um repasse espec�fico

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Maven** - Gerenciamento de depend�ncias
- **H2 Database** - Banco de dados em mem�ria para testes
- **JaCoCo** - Ferramenta para medi��o da cobertura de testes
- **JUnit 5** - Framework de testes
- **Mockito** - Framework para cria��o de mocks
- **Spring Data JPA** - Facilita a implementa��o de reposit�rios baseados em JPA
- **Spring Web** - Facilita a cria��o de aplica��es web
- **Lombok** - Biblioteca Java que ajuda a reduzir a quantidade de c�digo boilerplate
- **SpringDoc OpenAPI** - Gera��o de documenta��o OpenAPI
- **Liquibase** - Gerenciamento de mudan�as no esquema do banco de dados

## Como Executar

1. **Clone o reposit�rio:**

   ```bash
   git clone https://github.com/BrenoCesar1/testLabs.git
   cd TransfersAPI
   
2. **Configure as depend�ncias:**

   ```bash
   mvn clean install

3. **Execute aplica��o:**

   ```bash
   mvn spring-boot:run

A aplica��o estar� dispon�vel em http://localhost:8080.

3. **Execute os testes:**

   ```bash
   mvn test
   mvn jacoco:report
O relat�rio de cobertura estar� dispon�vel no caminho target/site/jacoco/index.html.

## Acesso a documenta��o
1. **Acesse a url para visualizar a documenta��o da API:**

   ```bash
   http://localhost:8080/swagger-ui/index.html

## EndPoints
1. **Cria um novo repasse:** 
    ```bash
    POST /v1/api/transfers
    ```
    Exemplo de corpo da requisi��o:
    ```json
   {
      "typeOfTransfer": "CONTABIL",
      "transferValue": 1500.00,
      "expirationDate": "2024-09-01",
      "paymentMethod": "BANK_TRANSFER",
      "sourceSystem": "LOGISTICA"
   }
    ```
   Retorno:
    ```json
   {
      "id": 1,
      "typeOfTransfer": "CONTABIL",
      "transferValue": 1500.00,
      "expirationDate": "2024-09-01",
      "paymentMethod": "BANK_TRANSFER",
      "sourceSystem": "LOGISTICA"
   }
    ```
2. **Atualiza um repasse pelo ID:**
    ```bash
    PUT /v1/api/transfers/{id}
    ```
    Exemplo de corpo da requisi��o:
    ```json
   {
      "id": 1,
      "typeOfTransfer": "CONCILIACAO"
   }
    ```
   Retorno:
    ```json
   {
      "id": 1,
      "typeOfTransfer": "CONCILIACAO",
      "transferValue": 1500.00,
      "expirationDate": "2024-09-01",
      "paymentMethod": "BANK_TRANSFER",
      "sourceSystem": "LOGISTICA"
   }
    ```
3. **Lista todos os repasses com filtros:**
 ```bash
 GET /v1/api/transfers
 ```
 Exemplo de par�metros de consulta:
 ```text
typeOfTransfer (opcional)
expirationDate (opcional)
paymentMethod (opcional)
sourceSystem (opcional)
orderBy (opcional) - Campo para ordena��o
expirationDataStart (opcional) - Data de in�cio para filtro de data de vencimento
expirationDataEnd (opcional) - Data de fim para filtro de data de vencimento
page (opcional) - P�gina de resultados
size (opcional) - N�mero de resultados por p�gina
   ```
 Exemplo de requisi��o com filtros:

 ```bash
 GET /v1/api/transfers?typeOfTransfer=SELLER&orderBy=expirationDate&page=0&size=10
 ```
    Retorno:
  ```json
   {
    "totalPages": 1,
    "content": [
        {
            "id": 1,
            "typeOfTransfer": "SELLER",
            "transferValue": 1800.00,
            "expirationDate": "01/09/2024 00:00",
            "paymentMethod": "CREDIT_CARD",
            "sourceSystem": "MOBILE",
            "createdAt": "19/08/2024 22:24",
            "updatedAt": "19/08/2024 22:24"
        }
    ],
    "totalElements": 1
   }
 ```
    
4. **Detalha um repasse pelo ID:**
    ```bash
   GET /v1/api/transfers/{id}
    ```
   Retorno:
    ```json
   {
      "id": 1,
      "typeOfTransfer": "CONTABIL",
      "transferValue": 1500.00,
      "expirationDate": "2024-09-01",
      "paymentMethod": "BANK_TRANSFER",
      "sourceSystem": "LOGISTICA"
   }
    ```
5. **Deleta um repasse pelo ID:**
    ```bash
    DELETE /v1/api/transfers/{id}
    ```

## Valida��es
As valida��es voc� encontra na aba schema na documenta��o da API, mas aqui est�o algumas delas:
1. **Existe valores determinados para os campos "typeOfTransfer", "paymentMethod" e "sourceSystem".**
2. **Valor da transfer�ncia � requerido: O campo transferValue � obrigat�rio.**
3. **Data de vencimento � requerida: O campo expirationDate � obrigat�rio e dever ser inserido no padr�o yyyy-MM-dd.**
4. **Data de vencimento n�o pode ser menor que a data atual.**