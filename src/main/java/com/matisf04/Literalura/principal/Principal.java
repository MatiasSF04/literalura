package com.matisf04.Literalura.principal;

import com.matisf04.Literalura.model.*;
import com.matisf04.Literalura.repository.AutorRepository;
import com.matisf04.Literalura.repository.LibroRepository;
import com.matisf04.Literalura.service.ConsumoAPI;
import com.matisf04.Literalura.service.ConvierteDatos;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<DatosLibro> listaLibros = new ArrayList<>();
    private LibroRepository repoLibro;
    private AutorRepository repoAutor;

    public Principal(LibroRepository repoLibro, AutorRepository repoAutor) {
        this.repoLibro = repoLibro;
        this.repoAutor = repoAutor;
    }

    public void mostrarMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    Seleccione una opción: 
                    1) Buscar libro por título
                    2) Listar libros registrados
                    3) Listar autores registrados
                    4) Listar autores vivos en un determinado año
                    5) Listar libros por idioma
                    0) Salir
                    
                    """;
            System.out.println(menu);
            String seleccion = teclado.nextLine();

            if(esEntero(seleccion)) {
                opcion = Integer.parseInt(seleccion);
            }

            switch (opcion) {
                case 0:
                    System.out.println("Saliste del programa, adiós...");
                    break;
                case 1:
                    buscarLibroXTitulo();
                    break;
                case 2:
                    listarLibrosBuscados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosXPeriodo();
                    break;
                case 5:
                    listarLibrosXIdioma();
                    break;
                default:
                    System.out.println("Seleccione una opción válida.");
            }
        }
    }

    private void buscarLibroXTitulo() {
        System.out.println("Escribe el nombre del libro que estás buscando:\n");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));

        if (json.isBlank()) {
            System.out.println("No se logró obtener un libro con ese nombre");
        } else {
            System.out.println(json);
        }

        DatosGeneral resultado = convierteDatos.obtenerDatos(json, DatosGeneral.class);

        if (resultado.libros().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
        }

        Optional<DatosLibro> datosLibro = resultado.libros().stream()
        .filter(d -> {
            boolean coincide = d.titulo().contains(nombreLibro);
            if (coincide) {
                System.out.println("Texto coincide con: " + nombreLibro);
                System.out.println(d.titulo());
            }

            coincide = repoLibro.findByTitulo(d.titulo()) == null;

            return coincide;
        })
        .findFirst();

        if (datosLibro.isPresent()) {
            Libro libro = new Libro(datosLibro.get());
            Libro libroExistente = repoLibro.findByTitulo(libro.getTitulo());

            if(libroExistente != null){
                System.out.println("Libro ya existe en base de datos");
                return;
            }

            Autor autor = repoAutor.findByNombre(libro.getAutor().getNombre());
            if (autor != null) {
                libro.setAutor(autor);
            } else {
                repoAutor.save(libro.getAutor());
            }

            repoLibro.save(libro);
        }
    }

    private void listarLibrosBuscados() {
        repoLibro.findAll().forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        repoAutor.findAll().forEach(System.out::println);
    }

    private void listarAutoresVivosXPeriodo() {
        System.out.println("Por favor ingresa el año tope para filtrar autores vivos.");
        int fecha = teclado.nextInt();
        teclado.nextLine();
        repoAutor.obtenerAutoresVivosXFecha(fecha).forEach(System.out::println);
    }

    private void listarLibrosXIdioma(){
        List<Libro> libros = repoLibro.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        List<String> idiomasDisponibles = libros.stream()
                .map(Libro::getIdioma)
                .distinct()
                .sorted()
                .toList();

        System.out.println("Seleccione un idioma:");
        for (int i = 0; i < idiomasDisponibles.size(); i++) {
            System.out.println((i + 1) + ") " + Idioma.traducirCodigo(idiomasDisponibles.get(i)));
        }

        int seleccion = teclado.nextInt();
        teclado.nextLine();

        if (seleccion < 1 || seleccion > idiomasDisponibles.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        String idiomaElegido = idiomasDisponibles.get(seleccion - 1);

        List<Libro> librosFiltrados = libros.stream()
                .filter(libro -> libro.getIdioma().equals(idiomaElegido))
                .toList();

        System.out.println("📚 Libros en idioma " + Idioma.traducirCodigo(idiomaElegido) + ":");
        librosFiltrados.forEach(System.out::println);
    }

    public static boolean esEntero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
