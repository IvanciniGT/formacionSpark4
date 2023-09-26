import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class ProcesarHashtagsConSpark {

    public static void main(String[] args) throws Exception {
        // PASO 1: Abrir una conexión con un cluster de Spark... Como si fuera una base de datos
        // Primero, establezco la configuración de la conexión
        SparkConf configuracion = new SparkConf()   // Creo un objeto para albergar la configuración (PATRON BUILDER)
                                        .setAppName("ProcesarHashtagsConSpark")           // Identifica mi app en el cluster.
                                      //.setMaster("spark://IP_DEL_MAESTRO:PUERTO");      // Contra que cluster trabajo
                                        .setMaster("local[2]"); // Esto levanta un cluster con Hadoop y Spark en nuestra máquina
                                                                // Dentro de la misma MV en la que corro el programa
                                                                // Esto está guay para desarrollo
        // Después creo la conexión
        JavaSparkContext conexion = new JavaSparkContext(configuracion);
        // PASO 2: Preparo los datos.... y los cargo en Spark
        List<String> tweets = Arrays.asList(
                "En la playa con mis amigos!!! (#goodVibes)",
                "Amig@s de mierda#goodvibes#summerLove",
                "Amigos guays #goodvibes#friendsLove",
                "Amigos de mierda que te cagas #badvibes#CacaFriends",
                "Amigos de mierda que te cagas #badvibes#PedoFriends"
        );
        List<String> prohibidas = Arrays.asList(
                "caca", "culo", "pedo", "pis"
        );

     // Stream<String> tweetsEnStream = tweets.stream();                                // Para cada tweet
     //    vv equiv en Spark a un Stream de JAVA
        JavaRDD<String> tweetsEnSpark  = conexion.parallelize(tweets);

        // PASO 3: Proceso los datos
        //List<String> hashtags =
                tweetsEnSpark
                .repartition(200)                                        // Para cada tweet
                .filter(tweet -> tweet.contains("#"))                           // Me quedo con los que tienen hashtag
                .map(tweet -> tweet.replace("#", " #"))       // Añadir un espacio delante del cuadradito
                .map(tweet -> tweet.split("[ .,_+(){}!?¿'\"<>/@|&-]+"))  // Separo las palabras y los hashtags
                .flatMap(arrayDePalabras -> Arrays.asList(arrayDePalabras).iterator()) // Convierto cada array en un Stream(map) y junto todos los streams en uno (flat)
              //.flatMap(arrayDePalabras -> Arrays.stream(arrayDePalabras))
              //.flatMap( Arrays::stream )
                    // El flatMap de Streams requiere una función que devuelva Un Stream
                    // El flatMap de JavaRDD requiere una función que devuelva un Iterable
                .filter(palabra -> palabra.startsWith("#"))                     // Me quedo con los que empiezan por #
                .map(hashtag -> hashtag.substring(1))                // Quito el cuadradito
                .map(String::toLowerCase)                                      // Convierto a minúsculas
                .filter(hashtag -> prohibidas.stream().noneMatch(hashtag::contains)) // Me quedo con los que no contienen palabras de la lista de prohibidas

                        // Llegados a ete punto, tenemos los hashtag

              //.collect(Collectors.toList());                                 // Lo convierto a una lista
                //.collect();                                                    // Lo convierto a una lista
                //.countByValue()
                //.forEach( (hashtag, cantidad) -> System.out.println(hashtag + " -> " + cantidad));
                        // LO QUE NOS QUEDA ES DE IDEA FELIZ ! TOTAL ! Tanto que da asco!
                .mapToPair(hashtag -> new Tuple2<>(hashtag, 1))                 // Añadir un 1 a cada hashtag
                .reduceByKey(Integer::sum)                                      // Sumar los 1s de cada hashtag
                .mapToPair( parejita ->  new Tuple2<>(parejita._2, parejita._1))  // Intercambiar el orden de la tupla para poder ordenar
                .sortByKey(false)                                       // Ordenar por el número de ocurrencias descendente
                .take(2)                                                // Es mi función de reducción
                .forEach( parejita -> System.out.println("Hashtag: " + parejita._2 + " mencionado en " + parejita._1 + " tweets"));

                // Al final sacar el resultado por pantalla
        // PASO 4: HAgo lo que sea necesario con el resultado.
        //hashtags.forEach(System.out::println);

        // PASO FINAL: Cerrar la conexión con el cluster de Spark
        conexion.close();
    }

}
