package com.app.testLabs.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("TransferDto", createTransferDtoSchema()));
    }

    private Schema<?> createTransferDtoSchema() {
        return new Schema<>()
                .description("DTO para criação de repasse")
                .addProperty("typeOfTransfer", new StringSchema()
                        .example("SELLER")
                        .description("Tipo de repasse: SELLER, CONCILIACAO, CONTABIL"))
                .addProperty("transferValue", new Schema<BigDecimal>()
                        .example(new BigDecimal("0.01"))
                        .description("O valor do repasse deve ser positivo!"))
                .addProperty("expirationDate", new StringSchema()
                        .example("2024-12-31")
                        .description("A data deve estar no formato yyyy-MM-dd"))
                .addProperty("paymentMethod", new StringSchema()
                        .example("CREDIT_CARD")
                        .description("Método de pagamento: CASH, PIX, CREDIT_CARD, DEBIT_CARD, BANK_SLIP, BANK_TRANSFER"))
                .addProperty("sourceSystem", new StringSchema()
                        .example("MOBILE")
                        .description("Sistema de origem: ECOM, MOBILE, LOGISTICA"));
    }
}
