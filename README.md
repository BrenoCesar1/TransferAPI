# Transfer API

Esta é uma API para gestão de transferências financeiras. A API foi desenvolvida em Java utilizando o framework Spring Boot 3 e Java 17. Ela permite gerenciar três tipos de transferências: `SELLER`, `CONCILIACAO` e `CONTABIL`.

## Funcionalidades

- Criar uma novo repasse
- Atualizar um repasse existente
- Excluir um repasse
- Consultar repasse com filtros (paginação e ordenação suportadas)
- Consultar um repasse específico

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Maven** - Gerenciamento de dependências
- **H2 Database** - Banco de dados em memória para testes
- **JaCoCo** - Ferramenta para medição da cobertura de testes
- **JUnit 5** - Framework de testes
- **Mockito** - Framework para criação de mocks
- **Spring Data JPA** - Facilita a implementação de repositórios baseados em JPA
- **Spring Web** - Facilita a criação de aplicações web
- **Lombok** - Biblioteca Java que ajuda a reduzir a quantidade de código boilerplate
- **SpringDoc OpenAPI** - Geração de documentação OpenAPI
- **Liquibase** - Gerenciamento de mudanças no esquema do banco de dados

## Como Executar

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/BrenoCesar1/testLabs.git
   cd TransferAPI
   
2. **Configure as dependências:**

   ```bash
   mvn clean install

3. **Execute aplicação:**

   ```bash
   mvn spring-boot:run

A aplicação estará disponível em http://localhost:8080.

3. **Execute os testes:**

   ```bash
   mvn test
   mvn jacoco:report
O relatório de cobertura estará disponível no caminho target/site/jacoco/index.html.

## Acesso a documentação
1. **Acesse a url para visualizar a documentação da API:**

   ```bash
   http://localhost:8080/swagger-ui/index.html

## EndPoints
1. **Cria um novo repasse:** 
    ```bash
    POST /v1/api/transfers
    ```
    Exemplo de corpo da requisição:
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
    Exemplo de corpo da requisição:
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
 Exemplo de parâmetros de consulta:
 ```text
typeOfTransfer (opcional)
expirationDate (opcional)
paymentMethod (opcional)
sourceSystem (opcional)
orderBy (opcional) - Campo para ordenação
expirationDataStart (opcional) - Data de início para filtro de data de vencimento
expirationDataEnd (opcional) - Data de fim para filtro de data de vencimento
page (opcional) - Página de resultados
size (opcional) - Número de resultados por página
   ```
 Exemplo de requisição com filtros:

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

## Validações
As validações você encontra na aba schema na documentação da API, mas aqui estão algumas delas:
1. **Existe valores determinados para os campos "typeOfTransfer", "paymentMethod" e "sourceSystem".**
2. **Valor da transferência é requerido: O campo transferValue é obrigatório.**
3. **Data de vencimento é requerida: O campo expirationDate é obrigatório e dever ser inserido no padrão yyyy-MM-dd.**
4. **Data de vencimento não pode ser menor que a data atual.**
