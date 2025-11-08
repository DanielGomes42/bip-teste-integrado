package com.example.backend.controller;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Benefícios", description = "CRUD de benefícios")
@RestController
@RequestMapping("/api/beneficios")
@CrossOrigin(origins = "http://localhost:4200")
public class BeneficioController {

    private final BeneficioRepository beneficioRepository;

    public BeneficioController(BeneficioRepository beneficioRepository) {
        this.beneficioRepository = beneficioRepository;
    }

    // GET /api/beneficios
    @Operation(summary = "Lista todos os benefícios")
    @GetMapping
    public List<Beneficio> listar() {
        return beneficioRepository.findAll();
    }

    // GET /api/beneficios/{id}
    @Operation(summary = "Busca um benefício pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> buscar(@PathVariable Long id) {
        return beneficioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/beneficios
    @Operation(summary = "Cria um novo benefício")
    @PostMapping
    public ResponseEntity<Beneficio> criar(@RequestBody Beneficio beneficio) {
        beneficio.setId(null); // garante create
        Beneficio salvo = beneficioRepository.save(beneficio);
        return ResponseEntity.ok(salvo);
    }

    // PUT /api/beneficios/{id}
    @Operation(summary = "Atualiza um benefício existente")
    @PutMapping("/{id}")
    public ResponseEntity<Beneficio> atualizar(@PathVariable Long id,
                                               @RequestBody Beneficio beneficio) {
        return beneficioRepository.findById(id)
                .map(existente -> {
                    existente.setNome(beneficio.getNome());
                    existente.setDescricao(beneficio.getDescricao());
                    existente.setValor(beneficio.getValor());
                    existente.setAtivo(beneficio.getAtivo());
                    Beneficio atualizado = beneficioRepository.save(existente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/beneficios/{id}
    @Operation(summary = "Remove um benefício")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!beneficioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        beneficioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
