package com.app.testLabs.application.service;

import com.app.testLabs.application.dto.TransferDto;
import com.app.testLabs.application.mapper.TransferMapper;
import com.app.testLabs.application.util.dateUtil.DateUtil;
import com.app.testLabs.infraestructure.entity.Transfer;
import com.app.testLabs.infraestructure.exception.ResourceNotFoundException;
import com.app.testLabs.infraestructure.repository.TransferRepository;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TransferService {

    private final TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public Transfer save(TransferDto transferDto) {
        Transfer transfer = TransferMapper.toEntity(transferDto);
        return transferRepository.save(transfer);
    }

    public Transfer update(TransferDto transferDto) {
        Optional.ofNullable(transferDto.getId())
                .orElseThrow(() -> new ValidationException("Id é requerido"));

        Transfer transfer = findById(transferDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Repasse não encontrado"));

        transfer.setTypeOfTransfer(Optional.ofNullable(transferDto.getTypeOfTransfer())
                .map(Transfer.TypeOfTransfer::valueOf)
                .orElse(transfer.getTypeOfTransfer()));
        transfer.setTransferValue(Optional.ofNullable(transferDto.getTransferValue())
                .orElse(transfer.getTransferValue()));
        transfer.setExpirationDate(transferDto.getExpirationDate() != null ?
                DateUtil.stringToDate(transferDto.getExpirationDate()) : transfer.getExpirationDate());
        transfer.setPaymentMethod(Optional.ofNullable(transferDto.getPaymentMethod())
                .map(Transfer.PaymentMethod::valueOf)
                .orElse(transfer.getPaymentMethod()));
        transfer.setSourceSystem(Optional.ofNullable(transferDto.getSourceSystem())
                .map(Transfer.SourceSystem::valueOf)
                .orElse(transfer.getSourceSystem()));

        return transferRepository.save(transfer);
    }

    public void delete(Long id) {
        findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repasse não encontrado"));

        transferRepository.deleteById(id);
    }

    public Page<Transfer> findAllByFilter(String typeOfTransfer, String expirationDateStart, String expirationDateEnd,
                                          String paymentMethod, String sourceSystem, String orderBy, Pageable pageable)
    {
        Transfer.TypeOfTransfer typeOfTransferEnum = typeOfTransfer != null ?
                Transfer.TypeOfTransfer.valueOf(typeOfTransfer) : null;
        Date expirationDateStartDate = expirationDateStart != null ?
                DateUtil.stringToDate(expirationDateStart) : null;
        Date expirationDateEndDate = expirationDateEnd != null ?
                DateUtil.stringToDate(expirationDateEnd) : null;
        Transfer.PaymentMethod paymentMethodEnum = paymentMethod != null ?
                Transfer.PaymentMethod.valueOf(paymentMethod) : null;
        Transfer.SourceSystem sourceSystemEnum = sourceSystem != null ?
                Transfer.SourceSystem.valueOf(sourceSystem) : null;

        switch (orderBy) {
            case "typeOfTransfer":
                return transferRepository.findAllByFilterOrderByTypeOfTransfer(
                        typeOfTransferEnum, expirationDateStartDate, expirationDateEndDate,
                        paymentMethodEnum, sourceSystemEnum, pageable);
            case "expirationDate":
                return transferRepository.findAllByFilterOrderByExpirationDate(
                        typeOfTransferEnum, expirationDateStartDate, expirationDateEndDate,
                        paymentMethodEnum, sourceSystemEnum, pageable);
            case "paymentMethod":
                return transferRepository.findAllByFilterOrderByPaymentMethod(
                        typeOfTransferEnum, expirationDateStartDate, expirationDateEndDate,
                        paymentMethodEnum, sourceSystemEnum, pageable);
            case "sourceSystem":
                return transferRepository.findAllByFilterOrderBySourceSystem(
                        typeOfTransferEnum, expirationDateStartDate, expirationDateEndDate,
                        paymentMethodEnum, sourceSystemEnum, pageable);
            default:
                return transferRepository.findAllByFilterOrderByTypeOfTransfer(
                        typeOfTransferEnum, expirationDateStartDate, expirationDateEndDate,
                        paymentMethodEnum, sourceSystemEnum, pageable);
        }
    }

    public Optional<Transfer> findById(Long id) {
        return transferRepository.findById(id);
    }

}
