package com.matisf04.Literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer descargas;
    private String idioma;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    @Override
    public String toString() {
        return "Libro Encontrado!\nTitulo= '" + titulo + '\'' +
                ", Descargas=" + descargas +
                ", Idioma=" + idioma +
                ", Autor=" + autor;
    }

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();

        if (datosLibro.autor() != null && !datosLibro.autor().isEmpty()) {
            DatosAutor datosAutor = datosLibro.autor().get(0); // Tomamos el primer autor
            this.autor = new Autor(datosAutor);
        } else {
            this.autor = new Autor(); // o null si preferís
            this.autor.setNombre("Autor desconocido");
        }

        this.idioma = datosLibro.idioma() != null && !datosLibro.idioma().isEmpty()
                ? Idioma.traducirCodigo(datosLibro.idioma().get(0))
                : "Idioma desconocido";
        this.descargas = datosLibro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
