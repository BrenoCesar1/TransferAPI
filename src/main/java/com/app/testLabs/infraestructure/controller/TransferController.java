package com.app.testLabs.infraestructure.controller;

import com.app.testLabs.application.dto.TransferDto;
import com.app.testLabs.application.mapper.TransferMapper;
import com.app.testLabs.application.service.TransferService;
import com.app.testLabs.infraestructure.entity.Transfer;
import com.app.testLabs.infraestructure.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/v1/api/transfers")
@Tag(name = "TransferController")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @Operation(summary = "Criar novo repasse", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Repasse criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody TransferDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(TransferMapper.toDto(transferService.save(request)));
    }


    @Operation(summary = "Atualizar repasse", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repasse criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
            @ApiResponse(responseCode = "404", description = "Repasse não encontrado")
    })
    @PutMapping
    public ResponseEntity<Object> update(@Valid @RequestBody TransferDto request) {
        return ResponseEntity.ok(TransferMapper.toDto(transferService.update(request)));
    }


    @Operation(summary = "Pesquisar repasse por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repasse encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Repasse não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Transfer transfer = transferService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repasse não encontrado"));

        return ResponseEntity.status(HttpStatus.OK).body(TransferMapper.toDto(transfer));
    }


    @Operation(summary = "Listar todos os repasses", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repasse encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Repasse não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<Object> findAll(
            Pageable pageable,
            @RequestParam(required = false) String typeOfTransfer,
            @RequestParam(required = false) String expirationDateStart,
            @RequestParam(required = false) String expirationDateEnd,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) String sourceSystem,
            @RequestParam(required = false, defaultValue = "id") String orderBy) {

        Page<Transfer> transfers = transferService.findAllByFilter(
                typeOfTransfer, expirationDateStart, expirationDateEnd, paymentMethod, sourceSystem, orderBy, pageable);

        if (transfers.hasContent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("content", TransferMapper.toDtoList(transfers.getContent()));
            response.put("totalElements", transfers.getTotalElements());
            response.put("totalPages", transfers.getTotalPages());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @Operation(summary = "Deletar repasse por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Repasse deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Repasse não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        transferService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
