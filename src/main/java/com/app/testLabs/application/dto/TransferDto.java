package com.app.testLabs.application.dto;

import com.app.testLabs.application.util.annotation.FutureDate;
import com.app.testLabs.application.util.annotation.ValidEnum;
import com.app.testLabs.infraestructure.entity.Transfer;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @ValidEnum(enumClass = Transfer.TypeOfTransfer.class, message = "Tipo de repasse inválido! " +
            "As opções válidas são: SELLER, CONCILIACAO, CONTABIL.")
    private String typeOfTransfer;

    @DecimalMin(value = "0.01", message = "O valor do repasse deve ser positivo!")
    private BigDecimal transferValue;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "A data deve estar no formato yyyy-MM-dd")
    @FutureDate
    private String expirationDate;

    @ValidEnum(enumClass = Transfer.PaymentMethod.class, message = "Método de pagamento inválido! " +
            "As opções válidas são: CASH, PIX, CREDIT_CARD, DEBIT_CARD, BANK_SLIP, BANK_TRANSFER.")
    private String paymentMethod;

    @ValidEnum(enumClass = Transfer.SourceSystem.class, message = "Sistema de origem inválido! " +
            "As opções válidas são: ECOM, MOBILE, LOGISTICA.")
    private String sourceSystem;
    private String dtCreation;
    private String dtUpdate;

}
