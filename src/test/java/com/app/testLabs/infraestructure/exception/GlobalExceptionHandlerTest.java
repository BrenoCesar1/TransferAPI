package com.app.testLabs.infraestructure.exception;

import com.app.testLabs.application.dto.TransferDto;
import com.app.testLabs.application.service.TransferService;
import com.google.gson.Gson;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferService service;

    @Test
    void whenResourceNotFoundExceptionThrown_thenReturns404() throws Exception {
        mockMvc.perform(get("/v1/api/transfers/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenValidationExceptionThrown_thenReturns400() throws Exception {
        Mockito.doThrow(new ValidationException("Validação falhou"))
                .when(service).save(Mockito.any());

        mockMvc.perform(post("/v1/api/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"invalidField\":\"invalidValue\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Validação falhou"));
    }

    @Test
    void whenInvalidFormatExceptionThrown_thenReturns400() throws Exception {
        TransferDto transferDto = buildErrorDto();
        String requestJson = new Gson().toJson(transferDto);

        mockMvc.perform(post("/v1/api/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenMethodArgumentNotValidExceptionThrown_thenReturns400WithErrors() throws Exception {
        TransferDto transferDto = buildErrorDto();
        transferDto.setPaymentMethod("");
        String requestJson = new Gson().toJson(transferDto);

        mockMvc.perform(post("/v1/api/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGeneralExceptionThrown_thenReturns500() throws Exception {
        Mockito.doThrow(new RuntimeException("Erro interno"))
                .when(service);

        mockMvc.perform(get("/v1/api/transfers"))
                .andExpect(status().isInternalServerError());
    }

    public static TransferDto buildErrorDto() {
        return TransferDto.builder()
                .id(1L)
                .expirationDate("21/11/2001")
                .typeOfTransfer("SELLER")
                .paymentMethod("CREDIT_CARD")
                .sourceSystem("MOBILE")
                .transferValue(BigDecimal.valueOf(100.00))
                .build();
    }


}
