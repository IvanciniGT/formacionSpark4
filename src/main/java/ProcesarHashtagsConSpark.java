import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProcesarHashtagsConSpark {

    public static void main(String[] args){

        List<String> tweets = Arrays.asList(
                "En la playa con mis amigos!!! (#goodVibes)" ,
                "Amig@s de mierda#goodvibes#summerLove",
                "Amigos guays #goodvibes#friendsLove",
                "Amigos de mierda que te cagas #badvibes#CacaFriends"
        );
         List<String> prohibidas = Arrays.asList(
                 "caca", "culo", "pedo", "pis"
         );

        List<String> hashtags = tweets.stream()                                 // Para cada tweet
                .filter( tweet -> tweet.contains("#") )                           // Me quedo con los que tienen hashtag
                .map( tweet -> tweet.replace("#", " #"))        // Añadir un espacio delante del cuadradito
                .map( tweet -> tweet.split("[ .,_+(){}!?¿'\"<>/@|&-]+"))   // Separo las palabras y los hashtags
                .flatMap( Arrays::stream )                                        // Convierto cada array en un Stream(map) y junto todos los streams en uno (flat)
                .filter( palabra -> palabra.startsWith("#") )                     // Me quedo con los que empiezan por #
                .map( hashtag -> hashtag.substring(1) )                // Quito el cuadradito
                .map( String::toLowerCase )                                      // Convierto a minúsculas
                .filter( hashtag -> prohibidas.stream().noneMatch( hashtag::contains ) ) // Me quedo con los que no contienen palabras de la lista de prohibidas
                .collect(Collectors.toList());                                   // Lo convierto a una lista

        hashtags.forEach(System.out::println);

    }

}
