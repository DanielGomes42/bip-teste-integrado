package com.example.backend.config;

import com.example.ejb.BeneficioEjbService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EjbConfig {

    @Bean
    public BeneficioEjbService beneficioEjbService() {
        // instancia a classe que veio do ejb-module
        return new BeneficioEjbService();
    }
}
