package com.matisf04.Literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

//@Entity
public class Autor {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    private String nombre;
    private Integer nacimiento;
    private Integer defuncion;
    //@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //private List<Libro> libros;

    @Override
    public String toString() {
        return "\nAutor: (" + nombre +
                ") Nace en " + nacimiento +
                "y muere en " + defuncion;
    }

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.nacimiento = OptionalInt.of(Integer.valueOf(datosAutor.nacimiento())).orElse(0);
        this.defuncion = OptionalInt.of(Integer.valueOf(datosAutor.defuncion())).orElse(0);
    }

    //public Autor(String nombre, Integer nacimiento, Integer defuncion) {
    //    this.nombre = libros.getFirst().getAutor().nombre;
    //    this.nacimiento = libros.getFirst().getAutor().nacimiento;
    //    this.defuncion = libros.getFirst().getAutor().defuncion;
    //    this.libros = libros.getFirst().getAutor().libros;
    //}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getDefuncion() {
        return defuncion;
    }

    public void setDefuncion(Integer defuncion) {
        this.defuncion = defuncion;
    }
}
