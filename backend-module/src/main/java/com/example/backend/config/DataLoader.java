//package com.example.backend.config;
//
//import com.example.backend.model.Beneficio;
//import com.example.backend.repository.BeneficioRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataLoader {
//
//    @Bean
//    CommandLineRunner loadData(BeneficioRepository repo) {
//        return args -> {
//            if (repo.count() == 0) {
//                Beneficio b1 = new Beneficio();
//                b1.setNome("Vale Alimentação");
//                b1.setDescricao("VA mensal");
//                b1.setValor(500.0);      // <- Double
//                b1.setAtivo(true);
//
//                Beneficio b2 = new Beneficio();
//                b2.setNome("Vale Refeição");
//                b2.setDescricao("VR mensal");
//                b2.setValor(600.0);      // <- Double
//                b2.setAtivo(true);
//
//                repo.save(b1);
//                repo.save(b2);
//            }
//        };
//    }
//}
