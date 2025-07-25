package com.matisf04.Literalura;

import com.matisf04.Literalura.principal.Principal;
import com.matisf04.Literalura.repository.AutorRepository;
import com.matisf04.Literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repoLibro;

	@Autowired
	private AutorRepository repoAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repoLibro, repoAutor);
		principal.mostrarMenu();
	}
}
