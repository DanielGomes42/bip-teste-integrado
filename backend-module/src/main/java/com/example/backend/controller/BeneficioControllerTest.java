package com.example.backend.controller;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BeneficioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BeneficioRepository repo;

    @Test
    void deveListarBeneficios() throws Exception {
        repo.save(new Beneficio(null, "Teste", "Desc", 100.0, true, 0L));

        mockMvc.perform(get("/api/beneficios"))
                .andExpect(status().isOk());
    }
}
