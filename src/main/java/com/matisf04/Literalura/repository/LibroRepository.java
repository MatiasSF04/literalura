package com.matisf04.Literalura.repository;

import com.matisf04.Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Libro findByTitulo(String titulo);

    @Query("SELECT libro FROM Libro libro WHERE libro.idioma = :idioma")
    List<Libro> obtenerLibrosPorIdioma(String idioma);
}