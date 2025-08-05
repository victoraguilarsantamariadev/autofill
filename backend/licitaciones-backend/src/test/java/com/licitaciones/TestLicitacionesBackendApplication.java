package com.licitaciones;

import org.springframework.boot.SpringApplication;

public class TestLicitacionesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(LicitacionesBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
