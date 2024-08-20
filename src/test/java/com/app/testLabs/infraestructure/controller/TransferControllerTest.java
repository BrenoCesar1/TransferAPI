package com.app.testLabs.infraestructure.controller;

import com.app.testLabs.application.dto.TransferDto;
import com.app.testLabs.application.mapper.TransferMapper;
import com.app.testLabs.application.service.TransferService;
import com.app.testLabs.infraestructure.entity.Transfer;
import com.app.testLabs.infraestructure.exception.GlobalExceptionHandler;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransferControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController transferController;

    private static final String URL_BASE = "/v1/api/transfers";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        TransferController transferController = new TransferController(transferService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transferController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void createTest() throws Exception {
        TransferDto transferDto = buildDto();
        Transfer transfer = TransferMapper.toEntity(transferDto);
        when(transferService.save(any(TransferDto.class))).thenReturn(transfer);
        String requestJson = new Gson().toJson(transferDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        verify(transferService, times(1)).save(any(TransferDto.class));
    }

    @Test
    void updateTest() throws Exception {
        TransferDto transferDto = buildDto();
        Transfer transfer = TransferMapper.toEntity(transferDto);

        when(transferService.update(any(TransferDto.class))).thenReturn(transfer);

        String requestJson = new Gson().toJson(transferDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(transferService, times(1)).update(any(TransferDto.class));
    }

    @Test
    void findAllSuccessTest() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Transfer> page = new PageImpl<>(Collections.singletonList(buildEntity()), pageable, 1);
        when(transferService.findAllByFilter(any(), any(), any(), any(), any(), any(), any())).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "?typeOfTransfer=SELLER")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(transferService, times(1)).findAllByFilter(any(), any(), any(), any(), any(),
                any(), any(Pageable.class));
    }

    @Test
    void findAllTestNotFound() throws Exception {
        Page<Transfer> page = new PageImpl<>(Collections.emptyList());
        when(transferService.findAllByFilter(any(), any(), any(), any(), any(), any(), any())).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    void findByIdTest() throws Exception {
        when(transferService.findById(any())).thenReturn(Optional.of(buildEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(transferService, times(1)).findById(any());
    }

    @Test
    void findByIdNotFoundTest() throws Exception {
        when(transferService.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        verify(transferService, times(1)).findById(any());
    }

    @Test
    void deleteTest() throws Exception {
        Long id = 1L;
        doNothing().when(transferService).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        verify(transferService, times(1)).delete(id);
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

    public static Transfer buildEntity() {
        return Transfer.builder()
                .expirationDate(new Date())
                .typeOfTransfer(Transfer.TypeOfTransfer.SELLER)
                .paymentMethod(Transfer.PaymentMethod.CASH)
                .sourceSystem(Transfer.SourceSystem.ECOM)
                .transferValue(BigDecimal.valueOf(100.00))
                .build();
    }

}
