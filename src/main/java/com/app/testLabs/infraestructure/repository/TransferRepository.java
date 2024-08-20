package com.app.testLabs.infraestructure.repository;

import com.app.testLabs.infraestructure.entity.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT t FROM Transfer t WHERE "
            + "(:typeOfTransfer IS NULL OR t.typeOfTransfer = :typeOfTransfer) AND "
            + "(:expirationDateStart IS NULL OR t.expirationDate >= :expirationDateStart) AND "
            + "(:expirationDateEnd IS NULL OR t.expirationDate <= :expirationDateEnd) AND "
            + "(:paymentMethod IS NULL OR t.paymentMethod = :paymentMethod) AND "
            + "(:sourceSystem IS NULL OR t.sourceSystem = :sourceSystem) "
            + "ORDER BY t.typeOfTransfer ASC")
    Page<Transfer> findAllByFilterOrderByTypeOfTransfer(
            @Param("typeOfTransfer") Transfer.TypeOfTransfer typeOfTransfer,
            @Param("expirationDateStart") Date expirationDateStart,
            @Param("expirationDateEnd") Date expirationDateEnd,
            @Param("paymentMethod") Transfer.PaymentMethod paymentMethod,
            @Param("sourceSystem") Transfer.SourceSystem sourceSystem,
            Pageable pageable);

    @Query("SELECT t FROM Transfer t WHERE "
            + "(:typeOfTransfer IS NULL OR t.typeOfTransfer = :typeOfTransfer) AND "
            + "(:expirationDateStart IS NULL OR t.expirationDate >= :expirationDateStart) AND "
            + "(:expirationDateEnd IS NULL OR t.expirationDate <= :expirationDateEnd) AND "
            + "(:paymentMethod IS NULL OR t.paymentMethod = :paymentMethod) AND "
            + "(:sourceSystem IS NULL OR t.sourceSystem = :sourceSystem) "
            + "ORDER BY t.expirationDate ASC")
    Page<Transfer> findAllByFilterOrderByExpirationDate(
            @Param("typeOfTransfer") Transfer.TypeOfTransfer typeOfTransfer,
            @Param("expirationDateStart") Date expirationDateStart,
            @Param("expirationDateEnd") Date expirationDateEnd,
            @Param("paymentMethod") Transfer.PaymentMethod paymentMethod,
            @Param("sourceSystem") Transfer.SourceSystem sourceSystem,
            Pageable pageable);

    @Query("SELECT t FROM Transfer t WHERE "
            + "(:typeOfTransfer IS NULL OR t.typeOfTransfer = :typeOfTransfer) AND "
            + "(:expirationDateStart IS NULL OR t.expirationDate >= :expirationDateStart) AND "
            + "(:expirationDateEnd IS NULL OR t.expirationDate <= :expirationDateEnd) AND "
            + "(:paymentMethod IS NULL OR t.paymentMethod = :paymentMethod) AND "
            + "(:sourceSystem IS NULL OR t.sourceSystem = :sourceSystem) "
            + "ORDER BY t.paymentMethod ASC")
    Page<Transfer> findAllByFilterOrderByPaymentMethod(
            @Param("typeOfTransfer") Transfer.TypeOfTransfer typeOfTransfer,
            @Param("expirationDateStart") Date expirationDateStart,
            @Param("expirationDateEnd") Date expirationDateEnd,
            @Param("paymentMethod") Transfer.PaymentMethod paymentMethod,
            @Param("sourceSystem") Transfer.SourceSystem sourceSystem,
            Pageable pageable);

    @Query("SELECT t FROM Transfer t WHERE "
            + "(:typeOfTransfer IS NULL OR t.typeOfTransfer = :typeOfTransfer) AND "
            + "(:expirationDateStart IS NULL OR t.expirationDate >= :expirationDateStart) AND "
            + "(:expirationDateEnd IS NULL OR t.expirationDate <= :expirationDateEnd) AND "
            + "(:paymentMethod IS NULL OR t.paymentMethod = :paymentMethod) AND "
            + "(:sourceSystem IS NULL OR t.sourceSystem = :sourceSystem) "
            + "ORDER BY t.sourceSystem ASC")
    Page<Transfer> findAllByFilterOrderBySourceSystem(
            @Param("typeOfTransfer") Transfer.TypeOfTransfer typeOfTransfer,
            @Param("expirationDateStart") Date expirationDateStart,
            @Param("expirationDateEnd") Date expirationDateEnd,
            @Param("paymentMethod") Transfer.PaymentMethod paymentMethod,
            @Param("sourceSystem") Transfer.SourceSystem sourceSystem,
            Pageable pageable);
}

