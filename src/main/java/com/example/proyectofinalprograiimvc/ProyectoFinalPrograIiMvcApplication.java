package com.example.proyectofinalprograiimvc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class ProyectoFinalPrograIiMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalPrograIiMvcApplication.class, args);
	}

}
