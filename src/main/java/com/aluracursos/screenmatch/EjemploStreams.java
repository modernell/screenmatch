
package com.aluracursos.screenmatch;

import java.util.Arrays;
import java.util.List;

import java.util.stream.*;

/**
 *
 * @author pablo
 */
public class EjemploStreams {
    public void muestraEjemplo(){
//         1ra forma de usar Stream. 
//         Atravez de una lista llamando al metodo Stream que contiene la interfaz collection
        List<String> nombres = Arrays.asList("Brenda","Luis","Maria Fernanda","Eric","Genesys");
        
        nombres.stream()
                .sorted()
                //.limit(3)
                .filter(n -> n.startsWith("L"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
        
//        2da. Forma de usar Stream; atravez del metodo of de la clase Stream
//        explicado en: https://www.youtube.com/watch?v=ACQtz4zpBLE
//        Stream<String> nombres = Stream.of("Brenda","Luis","Maria Fernanda","Eric","Genesys");
//        List<String> listaNombres = nombres.collect(Collectors.toList());
//        System.out.println(listaNombres);
                
    }
    
}
