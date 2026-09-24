package com.example.exemplocrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal do Spring Boot.
 * Inicia o contexto de aplicação e as configurações do banco H2 em memória.
 */
@SpringBootApplication
public class ExemploCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExemploCrudApplication.class, args);
    }
}