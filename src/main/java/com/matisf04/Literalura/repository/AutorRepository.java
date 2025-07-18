/*package com.matisf04.Literalura.repository;

import com.matisf04.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    public Autor findByNombre(String nombre);

    @Query("SELECT autor FROM Autor autor WHERE autor.anioNacimiento <= :anio AND autor.anioMuerte > :anio ")
    List<Autor> obtenerAutoresVivosEnAnio(Integer anio);
}
 */