package com.matisf04.Literalura.principal;

import com.matisf04.Literalura.model.*;
import com.matisf04.Literalura.service.ConsumoAPI;
import com.matisf04.Literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<DatosLibro> listaLibros = new ArrayList<>();

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

    private DatosLibro obtenerDatosLibro() {
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
            return null;
        }

        DatosLibro datos = resultado.libros().get(0);
        return datos;
    }

    private void buscarLibroXTitulo() {
        DatosLibro datos = obtenerDatosLibro();
        if (datos == null) {
            return;
        }

        listaLibros.add(datos);

        Libro libro = new Libro(datos);
        System.out.println(libro);
    }

    private void listarLibrosBuscados() {
        List<Libro> Libros = new ArrayList<>();
        Libros = listaLibros.stream()
                .map(d -> new Libro(d))
                .collect(Collectors.toList());

        Libros.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        Set<String> nombresProcesados = new HashSet<>();

        listaLibros.stream()
                .flatMap(libro -> libro.autor().stream())
                .filter(autor -> autor.nombre() != null && !nombresProcesados.contains(autor.nombre()))
                .peek(autor -> nombresProcesados.add(autor.nombre()))
                .forEach(autor -> {
                    System.out.println("\nAutor: " + autor.nombre());
                    System.out.println("  Nacimiento: " + (autor.nacimiento() != null ? autor.nacimiento() : "Desconocido"));
                    System.out.println("  Defunción: " + (autor.defuncion() != null ? autor.defuncion() : "Desconocida"));
                });

        if (nombresProcesados.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
        }
    }

    private void listarAutoresVivosXPeriodo() {
        Set<String> listaAutoresPeriodo = new HashSet<>();
        System.out.println("Por favor ingresa el año tope para filtrar autores vivos.");
        Integer periodo = teclado.nextInt();
        teclado.nextLine();

        listaLibros.stream()
                .flatMap(libro -> libro.autor().stream())
                .filter(autor -> autor.defuncion() > periodo)
                .peek(autor -> listaAutoresPeriodo.add(autor.nombre()))
                .forEach(autor -> {
                    System.out.println("\nAutor: " + autor.nombre());
                    System.out.println("  Nacimiento: " + (autor.nacimiento() != null ? autor.nacimiento() : "Desconocido"));
                    System.out.println("  Defunción: " + (autor.defuncion() != null ? autor.defuncion() : "Desconocida"));
                });
        if (listaAutoresPeriodo.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
        }
    }

    private void listarLibrosXIdioma(){
            if (listaLibros.isEmpty()) {
                System.out.println("No hay libros registrados.");
                return;
            }

            // Mapear DatosLibro → Libro (para que tenga idioma largo)
            List<Libro> libros = listaLibros.stream()
                    .map(Libro::new)
                    .toList();

            // Contar cuántos hay en español e inglés (por ejemplo)
            /*long cantidadEspanol = libros.stream()
                    .filter(libro -> libro.getIdioma().equalsIgnoreCase("español"))
                    .count();

            long cantidadIngles = libros.stream()
                    .filter(libro -> libro.getIdioma().equalsIgnoreCase("inglés"))
                    .count();*/
            // Agrupar por idioma y contar cuántos hay de cada uno
            Map<String, Long> cantidadPorIdioma = libros.stream()
                    .collect(Collectors.groupingBy(Libro::getIdioma, Collectors.counting()));

            /*System.out.println("📚 Cantidad de libros por idioma:");
            System.out.println("Español: " + cantidadEspanol);
            System.out.println("Inglés: " + cantidadIngles);*/
            System.out.println("📚 Cantidad de libros por idioma:");
            cantidadPorIdioma.forEach((idioma, cantidad) ->
                System.out.println("- " + idioma + ": " + cantidad));
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
