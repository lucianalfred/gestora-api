package com.ilungi.gestora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class GestoraApplication {
	
	 @PostConstruct
	    public void init() {
	        try {
	          
	            Class.forName("org.postgresql.Driver");
	            System.out.println("PostgreSQL Driver carregado com sucesso!");
	        } catch (ClassNotFoundException e) {
	            System.err.println("Erro ao carregar PostgreSQL Driver: " + e.getMessage());
	        }
	    }

	public static void main(String[] args) {
		SpringApplication.run(GestoraApplication.class, args);
	}

}
