package com.example.backend.service;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficioService {

    private final BeneficioRepository beneficioRepository;

    public BeneficioService(BeneficioRepository beneficioRepository) {
        this.beneficioRepository = beneficioRepository;
    }

    public List<Beneficio> listarTodos() {
        return beneficioRepository.findAll();
    }

    public Beneficio buscarPorId(Long id) {
        return beneficioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefício não encontrado: " + id));
    }

    public Beneficio criar(Beneficio beneficio) {
        beneficio.setId(null); // garante create
        return beneficioRepository.save(beneficio);
    }

    public Beneficio atualizar(Long id, Beneficio beneficioAtualizado) {
        Beneficio existente = buscarPorId(id);

        existente.setNome(beneficioAtualizado.getNome());
        existente.setDescricao(beneficioAtualizado.getDescricao());
        existente.setValor(beneficioAtualizado.getValor());
        existente.setAtivo(beneficioAtualizado.getAtivo());

        return beneficioRepository.save(existente);
    }

    public void deletar(Long id) {
        beneficioRepository.deleteById(id);
    }
}
