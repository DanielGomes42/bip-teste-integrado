package com.example.backend.controller;

import com.example.backend.model.TransferenciaRequest;
import com.example.ejb.BeneficioEjbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    private final BeneficioEjbService beneficioEjbService;

    public TransferenciaController(BeneficioEjbService beneficioEjbService) {
        this.beneficioEjbService = beneficioEjbService;
    }

    @PostMapping
    public ResponseEntity<Void> transferir(@RequestBody TransferenciaRequest request) {

        // aqui NÃO precisa converter, porque o request já tem BigDecimal
        BigDecimal valor = request.getValor();

        beneficioEjbService.transfer(
                request.getOrigemId(),
                request.getDestinoId(),
                valor
        );

        return ResponseEntity.noContent().build();
    }
}
