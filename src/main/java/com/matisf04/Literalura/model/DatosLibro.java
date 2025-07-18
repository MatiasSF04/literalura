package com.matisf04.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(@JsonAlias("title") String titulo,
                         @JsonAlias("download_count") Integer descargas,
                         @JsonAlias("languages") List<String> idioma,
                         @JsonAlias("authors") List<DatosAutor> autor) {
}