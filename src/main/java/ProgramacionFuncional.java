import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProgramacionFuncional {

    public static void main(String[] args){
        // Conceptos básicos de programación funcional
        Function<Integer, Integer> miFuncion = ProgramacionFuncional::doble;
        System.out.println(miFuncion.apply(5));
        Function<Integer, Integer> triple = numero -> numero * 3;
        System.out.println(triple.apply(5));
        Function<Integer, Integer> cuadrado = numero -> {
            // Podría poner más líneas de código aquí
            return numero * numero;
        };
        System.out.println(cuadrado.apply(5));
        imprimirSaludo(ProgramacionFuncional::componerSaludo, "Pepe");
        // Para qué sirve esto. Esto sirve cuando quiero escribir código (funciones)
        // Que tienen que hacer algo que conozco... pero que no sé exactamente qué del todo
        // Parte del código me lo pasan como argumento
        // Esto es muy útil.

        List<Integer> numeros = Arrays.asList(1,2,3,4,5,6,7,8,9,10); // Java 11 List.of()
        // Bucle sobre la lista ... como lo montábamos antes de Java 1.5
        for(int i = 0; i < numeros.size(); i++){
            System.out.println(numeros.get(i));
        }
        // En Java 1.5 se añade el Interfaz Iterable. Y desde entonces podemos usar los FOR-EACH
        for(Integer numero : numeros){
            System.out.println(numero);
        }
        // En Java 1.8, se añade programación funcional... lo que permite definir funciones
        // que se pueden pasar como argumentos a otras funciones
        // Y en el API de Java se empieza a usar intensivamente
        // Bucle desde Java 1.8
        numeros.forEach(System.out::println);

        // En Java 1.8, aparece también la clase Stream
        // Un Stream es una secuencia de datos preparada para ser procesada mediante programación funcional
        // Cualquier Collection (List, Set, Map, etc) tiene un método .stream()
        // Que convierte la colección en un Stream                                  collection.stream() -> Stream
        Stream<Integer> miStream = numeros.stream();
        // Igual que cualquier puedo generar un Stream desde cualquier colección
        // También puedo generar una colección desde un Stream
        // Mediante la función .collect( COLECTOR )                       stream.collect( COLECTOR ) -> Collection
        List<Integer> miListaOtraVez = miStream.collect( Collectors.toList() );
        // La gracia de los Streams es que me permiten aplicar un modelo de programación MAP-REDUCE
        numeros.stream()                            // Para cada numero
               .map( ProgramacionFuncional::doble ) // Calculo el doble. Esto apunta que hay que generar esa lista de dobles
               .map( numero -> numero - 5 )         // Le quito 5
               .map( numero -> numero * numero )    // Y lo elevo al cuadrado
               .filter( numero -> numero % 2 == 0 )  // Me quedo solo con los pares
               .sorted()                            // Ordeno
               .forEach( System.out::println );     // Y ahora lo imprimo.                 Este imprime... YA ... y para ello, necesita YA los dobles.
                                                                                        // Ahora es cuando se ejecuta el MAP
        List<String> textos = Arrays.asList("Buenos días", "Buenas tardes", "Buenas noches amigos");
        textos.stream()                                       //      Stream<String>
              .map( texto -> texto.split(" ") )         //      Stream<String[]>
              .flatMap( Arrays::stream )                      //      Stream<Stream<String>>
              .forEach( System.out::println );


        // Montar un programa que preprocese Tweets de Tweeter (X)
        // Para quedarme con los hashtag que no contengan palabra de una lista prohibida
        List<String> tweets = Arrays.asList(
                "En la playa con mis amigos!!! (#goodVibes)" ,
                "Amig@s de mierda#goodvibes#summerLove",
                "Amigos guays #goodvibes#friendsLove",
                "Amigos de mierda que te cagas #badvibes#CacaFriends"
        );
         List<String> prohibidas = Arrays.asList(
                 "caca", "culo", "pedo", "pis"
         );

         /*
         Resultado:  Una lista conteniendo:
          goodvibes
          goodvibes
          summerLove
          goodvibes
          friendslove
          badvibes
         */ /* -> Calcuremos los trending topics de tweeter)
         */
    }

    public static void imprimirSaludo(Function<String, String> generadorDelSaludo, String nombre){
        System.out.println(generadorDelSaludo.apply(nombre));
    }

    private static String componerSaludo(String nombre){
        return "Hola " + nombre;
    }

    public static int doble(int x){
        return x*2;
    }

}
