package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.Episodio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.*;
import java.util.*;



/**
 *
 * @author pablo
 */
public class Principal {
 
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE  = "https://www.omdbapi.com/?t="; 
    private final String API_KEY  = "TU-APIKEY-OMDB"; 
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    
    public void muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar: ");
        // Busca los datos generales de la Serie
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);
        
        // Busca los datos de todas las temporadas
        
        List<DatosTemporadas> temporadas = new ArrayList<>();
        
        for (int i = 1; i <= datos.totalDeTemporadas() ; i++){
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season="+i+ API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            //DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        
        //temporadas.forEach(System.out::println);
        
        // Mostrar solo el titulo de las temporadas
        
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());                
//            }            
//        } // fin for i
        
// Mejoria utilizando funciones lambda
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); 
        
        // Convertir todas las informaciones a una lista del tipo DatosEpisodios
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
        
        // Top 5 episodios
        System.out.println("Top 5 episodios");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primer Filtro (N/A)" + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .peek(e -> System.out.println("Segundo Ordenacion (M>m)" + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Tercer Filtro Mayuscula (m>M)" + e))
                .limit(5)
                .forEach(System.out::println);
        
        //Convirtiendo los datos a una lista del tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        //episodios.forEach(System.out::println);
                
        // Busqueda de episodios a partir de x año
//        System.out.println("a partir de que año deseas ver los episodios?");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Fecha de Lanzamiento: " + e.getFechaDeLanzamiento().format(dtf)
//                ));  

        //Busca episodios por un pedazo del título
        System.out.println("Por favor escriba el titulo del episodio que desea ver");
        var pedazoTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println(" Episodio encontrado");
            System.out.println("Los datos son: " + episodioBuscado.get());
        } else {
            System.out.println("Episodio no encontrado");
        }
        // Muestra evaluaciones de todas las temporadas
        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);
      
        //Calcular estadísticas de las evaluaciones de los episodios
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media " + est.getAverage());
        System.out.println("Mejor episódio: " + est.getMax());
        System.out.println("Peor episódio: " + est.getMin());
        System.out.println("Cantidad episódio: " + est.getCount());
    }
    
}
