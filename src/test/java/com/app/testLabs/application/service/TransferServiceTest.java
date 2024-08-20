package com.app.testLabs.application.service;

import com.app.testLabs.application.dto.TransferDto;
import com.app.testLabs.application.mapper.TransferMapper;
import com.app.testLabs.infraestructure.entity.Transfer;
import com.app.testLabs.infraestructure.exception.ResourceNotFoundException;
import com.app.testLabs.infraestructure.repository.TransferRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTransfer() {
        TransferDto transferDto = buildDto();
        Transfer transfer = TransferMapper.toEntity(transferDto);

        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);

        Transfer savedTransfer = transferService.save(transferDto);

        assertEquals(transfer.getId(), savedTransfer.getId());
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    void testUpdateTransfer() {
        TransferDto transferDto = buildDto();
        Transfer existingTransfer = TransferMapper.toEntity(transferDto);

        when(transferRepository.findById(anyLong())).thenReturn(Optional.of(existingTransfer));
        when(transferRepository.save(any(Transfer.class))).thenReturn(existingTransfer);

        Transfer updatedTransfer = transferService.update(transferDto);

        assertEquals(transferDto.getId(), updatedTransfer.getId());
        verify(transferRepository, times(1)).findById(transferDto.getId());
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    void testDeleteTransfer() {
        TransferDto transferDto = buildDto();
        Transfer existingTransfer = TransferMapper.toEntity(transferDto);

        when(transferRepository.findById(anyLong())).thenReturn(Optional.of(existingTransfer));
        doNothing().when(transferRepository).deleteById(anyLong());

        transferService.delete(transferDto.getId());

        verify(transferRepository, times(1)).findById(transferDto.getId());
        verify(transferRepository, times(1)).deleteById(transferDto.getId());
    }

    @Test
    void testUpdateTransferThrowsResourceNotFoundException() {
        TransferDto transferDto = buildDto();

        when(transferRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transferService.update(transferDto));

        verify(transferRepository, times(1)).findById(transferDto.getId());
    }

    @Test
    void testFindAllByFilterOrderByTypeOfTransfer() {
        Transfer transfer1 = new Transfer();
        transfer1.setId(1L);
        transfer1.setTypeOfTransfer(Transfer.TypeOfTransfer.SELLER);
        transfer1.setExpirationDate(new Date());

        Transfer transfer2 = new Transfer();
        transfer2.setId(2L);
        transfer2.setTypeOfTransfer(Transfer.TypeOfTransfer.SELLER);
        transfer2.setExpirationDate(new Date());

        List<Transfer> transfers = Arrays.asList(transfer1, transfer2);
        Page<Transfer> transferPage = new PageImpl<>(transfers);

        when(transferRepository.findAllByFilterOrderByTypeOfTransfer(
                eq(Transfer.TypeOfTransfer.SELLER),
                any(Date.class),
                any(Date.class),
                any(),
                any(),
                any(Pageable.class)))
                .thenReturn(transferPage);

        Page<Transfer> result = transferService.findAllByFilter(
                "SELLER",
                "2024-01-01",
                "2024-12-31",
                null,
                null,
                "typeOfTransfer",
                Pageable.unpaged());

        assertEquals(2, result.getTotalElements());
        assertEquals(Transfer.TypeOfTransfer.SELLER, result.getContent().get(0).getTypeOfTransfer());
    }

    @Test
    void testDeleteWhenTransferIsNotFound() {
        Long transferId = 1L;

        when(transferRepository.findById(transferId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transferService.delete(transferId));

        verify(transferRepository, times(1)).findById(transferId);
        verify(transferRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateWhenIdIsNull() {
        TransferDto transferDto = buildDto();
        transferDto.setId(null);

        assertThrows(ValidationException.class, () -> transferService.update(transferDto));

        verify(transferRepository, never()).findById(anyLong());
        verify(transferRepository, never()).save(any(Transfer.class));
    }

    public static TransferDto buildDto() {
        return TransferDto.builder()
                .id(1L)
                .expirationDate("2024-12-31")
                .typeOfTransfer("SELLER")
                .paymentMethod("CREDIT_CARD")
                .sourceSystem("MOBILE")
                .transferValue(BigDecimal.valueOf(100.00))
                .build();
    }

}
