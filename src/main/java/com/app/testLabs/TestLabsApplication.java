package com.app.testLabs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API de Transferências", version = "1.0.0", description = "Documentação da API para gerenciamento de transferências."))
public class TestLabsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestLabsApplication.class, args);
	}

}
