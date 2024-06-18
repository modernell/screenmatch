package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.service.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
                
                //System.out.println("Hola Maven");
	}
    // esta es mi apikey de omdbapi -> f633594b
    @Override
    public void run(String... args) throws Exception {
//        var consumoApi = new ConsumoAPI();
//        var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=f633594b");
//        
//        System.out.println(json);
//        
//        ConvierteDatos conversor = new ConvierteDatos();
//        var datos = conversor.obtenerDatos(json,DatosSerie.class);
//        
//        System.out.println(datos);
//        
//        json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=f633594b");
//        DatosEpisodio episodios = conversor.obtenerDatos(json, DatosEpisodio.class);
//        System.out.println(episodios);

        Principal principal = new Principal();
        principal.muestraElMenu();
//        
//        EjemploStreams ejemploStreams = new EjemploStreams();
//        ejemploStreams.muestraEjemplo();


        
    }

}

