package com.app.testLabs.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "transfer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_of_transfer")
    @Enumerated(EnumType.STRING)
    private TypeOfTransfer typeOfTransfer;

    @Column(name = "transfer_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal transferValue;

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "source_system", nullable = false)
    @Enumerated(EnumType.STRING)
    private SourceSystem sourceSystem;

    @Column(name = "dt_Creation")
    private Date dtCreation;

    @Column(name = "dt_Update")
    private Date dtUpdate;

    @PrePersist
    public void onSave() {
        this.dtCreation = Calendar.getInstance().getTime();
    }

    @PreUpdate
    public void onUpdate() {
        this.dtUpdate = Calendar.getInstance().getTime();
    }

    public enum TypeOfTransfer {
        SELLER,
        CONCILIACAO,
        CONTABIL
    }

    public enum PaymentMethod {
        CASH,
        PIX,
        CREDIT_CARD,
        DEBIT_CARD,
        BANK_SLIP,
        BANK_TRANSFER
    }

    public enum SourceSystem {
        ECOM,
        MOBILE,
        LOGISTICA
    }
}