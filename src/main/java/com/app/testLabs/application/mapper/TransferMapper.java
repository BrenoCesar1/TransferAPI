package com.app.testLabs.application.mapper;

import com.app.testLabs.application.dto.TransferDto;
import com.app.testLabs.application.util.dateUtil.DateUtil;
import com.app.testLabs.infraestructure.entity.Transfer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransferMapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private TransferMapper(){
    }

    public static Transfer toEntity(TransferDto transferDto, Optional<Date> expirationDate) {
        return Transfer.builder()
                            .id(transferDto.getId())
                            .typeOfTransfer(Transfer.TypeOfTransfer.valueOf(transferDto.getTypeOfTransfer()))
                            .transferValue(transferDto.getTransferValue())
                            .expirationDate(expirationDate.orElseGet(() -> DateUtil.stringToDate(transferDto.getExpirationDate())))
                            .paymentMethod(Transfer.PaymentMethod.valueOf(transferDto.getPaymentMethod()))
                            .sourceSystem(Transfer.SourceSystem.valueOf(transferDto.getSourceSystem()))
                        .build();
    }

    public static Transfer toEntity(TransferDto transferDto) {
        return toEntity(transferDto, Optional.empty());
    }

    public static TransferDto toDto(Transfer transfer) {
        return TransferDto.builder()
                                .id(transfer.getId())
                                .typeOfTransfer(transfer.getTypeOfTransfer().name())
                                .transferValue(transfer.getTransferValue())
                                .expirationDate(DATE_FORMAT.format(transfer.getExpirationDate()))
                                .paymentMethod(transfer.getPaymentMethod().name())
                                .sourceSystem(transfer.getSourceSystem().name())
                                .dtCreation(transfer.getDtCreation() != null ? DATE_FORMAT.format(transfer.getDtCreation()) : null)
                                .dtUpdate(transfer.getDtUpdate() != null ? DATE_FORMAT.format(transfer.getDtUpdate()) : null)
                            .build();
    }

    public static List<TransferDto> toDtoList(List<Transfer> transfers) {
        return transfers.stream()
                .map(TransferMapper::toDto)
                .collect(Collectors.toList());
    }
}
