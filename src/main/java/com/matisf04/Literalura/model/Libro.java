package com.matisf04.Literalura.model;

import jakarta.persistence.*;

//@Entity
//@Table(name = "libros")
public class Libro {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id", nullable = false)
    //private Long id;
    private String titulo;
    private Integer descargas;
    private String idioma;
    //@ManyToOne(cascade = CascadeType.ALL)
    private String autor;

    @Override
    public String toString() {
        return "Libro Encontrado!\nTitulo= '" + titulo + '\'' +
                ", Descargas=" + descargas +
                ", Idioma=" + idioma +
                ", Autor=" + autor;
    }

    public Libro() {}

    /*
    public Libro(DatosLibro datosLibro, DatosAutor datosAutor) {
        this.titulo = datosLibro.titulo();
        this.descargas = datosLibro.descargas();
        this.idioma = Idioma.obtenerIdioma(datosLibro.idioma().getFirst());
        this.autor = datosAutor.nombre();
    }*/

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        if (datosLibro.autor() != null && !datosLibro.autor().isEmpty()) {
            this.autor = datosLibro.autor().get(0).nombre();
        } else {
            this.autor = "Autor desconocido";
        }
        this.idioma = datosLibro.idioma() != null && !datosLibro.idioma().isEmpty()
                ? Idioma.traducirCodigo(datosLibro.idioma().get(0))
                : "Idioma desconocido";
        this.descargas = datosLibro.descargas();
    }


    /*
    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();

        if (datosLibro.autor() != null && !datosLibro.autor().isEmpty()) {
            DatosAutor datosAutor = datosLibro.autor().get(0);
            this.autor = new Autor(
                    datosAutor.nombre(),
                    datosAutor.nacimiento(),
                    datosAutor.defuncion()
            );
        }

        String codigoIdioma = datosLibro.idioma().isEmpty() ? "desconocido" : datosLibro.idioma().get(0);
        this.idioma = Idioma.obtenerIdioma(codigoIdioma);
        this.descargas = datosLibro.descargas();
    }
    */

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
